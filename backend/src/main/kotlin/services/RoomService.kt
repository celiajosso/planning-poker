import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import org.bson.types.ObjectId
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.User

class RoomService(private val db: MongoDatabase) {
    private val rooms = db.getCollection("rooms", Room::class.java)
    private val activeRooms = mutableMapOf<String, RoomInstance>()

    fun create(room: Room): String {
        rooms.insertOne(room)
        activeRooms[room.id!!] = RoomInstance(room)
        return room.id ?: ""
    }

    fun getAll(): List<Room> = rooms.find().toList()

    fun getById(id: String): Room? =
        rooms.find(eq("_id", ObjectId(id))).firstOrNull()

    fun update(id: String, updatedRoom: Room): Boolean {
        val result = rooms.replaceOne(eq("_id", ObjectId(id)), updatedRoom)
        if (result.modifiedCount > 0) {
            activeRooms[id]?.room = updatedRoom
            return true
        }
        return false
    }

    fun delete(id: String): Boolean {
        val result = rooms.deleteOne(eq("_id", ObjectId(id)))
        if (result.deletedCount > 0) {
            activeRooms.remove(id)
            return true
        }
        return false
    }

    fun joinRoom(roomId: String, user: User, session: DefaultWebSocketServerSession) {
        val roomInstance = activeRooms[roomId] ?: return
        roomInstance.join(user, session)
    }

    fun quitRoom(roomId: String, userId: String) {
        val roomInstance = activeRooms[roomId] ?: return
        roomInstance.quit(userId)
        removeIfEmpty(roomId)
    }

    private fun removeIfEmpty(roomId: String) {
        val roomInstance = activeRooms[roomId]
        if (roomInstance != null && roomInstance.isEmpty()) {
            activeRooms.remove(roomId)
        }
    }
}

class RoomInstance(
    var room: Room
) {
    private val users = mutableMapOf<String, User>()
    private val sessions = mutableMapOf<String, DefaultWebSocketServerSession>()

    fun join(user: User, session: DefaultWebSocketServerSession) {
        val userId = user.id ?: return
        users[userId] = user
        sessions[userId] = session

        session.sendSerialized("roomJoined", mapOf("id" to room.id))

        users.forEach { (otherId, otherUser) ->
            if (otherId != userId) {
                sessions[otherId]?.sendSerialized("playerJoined", mapOf("id" to userId, "name" to user.username))
                session.sendSerialized("playerJoined", mapOf("id" to otherId, "name" to otherUser.username))
            }
        }
    }

    fun quit(userId: String) {
        users.remove(userId)
        sessions.remove(userId)
    }

    fun isEmpty(): Boolean = users.isEmpty()

    suspend fun DefaultWebSocketServerSession.sendSerialized(event: String, data: Map<String, Any?>) {
        val message = Json.encodeToString(mapOf("event" to event, "data" to data))
        send(message)
    }
}
