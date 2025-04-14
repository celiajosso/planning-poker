import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import org.bson.types.ObjectId

class RoomService(private val db: MongoDatabase) {
    private val rooms = db.getCollection("rooms", Room::class.java)

    fun create(room: Room): String {
        rooms.insertOne(room)
        return room.id ?: ""
    }

    fun getAll(): List<Room> = rooms.find().toList()

    fun getById(id: String): Room? =
        rooms.find(eq("_id", ObjectId(id))).firstOrNull()

    fun update(id: String, updatedRoom: Room): Boolean {
        val result = rooms.replaceOne(eq("_id", ObjectId(id)), updatedRoom)
        return result.modifiedCount > 0
    }

    fun delete(id: String): Boolean {
        val result = rooms.deleteOne(eq("_id", ObjectId(id)))
        return result.deletedCount > 0
    }

}
