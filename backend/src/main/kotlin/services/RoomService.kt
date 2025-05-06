import com.example.models.*
import io.ktor.server.websocket.*
import services.StoryService
import services.UserService
import java.util.*
import com.mongodb.client.MongoDatabase

class RoomService {
    companion object {
        private val rooms = mutableMapOf<String, Room>()
        private lateinit var storyService: StoryService

        fun init(db: MongoDatabase) {
            storyService = StoryService(db)
        }

        suspend fun create(session: DefaultWebSocketServerSession, reqUser: User, reqRoom: Room) {
            val user = UserService.create(session, reqUser, Role.Administrator)

            val id = UUID.randomUUID().toString()
            val room = Room(id, reqRoom.name, administrator = user)
            rooms[id] = room

            session.sendSerialized(SocketMessage("RoomCreated", room = room, user = user, story = null))
        }

        fun getById(roomId: String): Room? {
            return rooms[roomId]
        }

        suspend fun joinRoom(user: User) {
            val room = rooms.getValue(user.roomId)
            room.users += user

            user.session.sendSerialized("roomJoined")

            for (otherUser in room.users) {
                if (user.session != otherUser.session) {
                    otherUser.session.sendSerialized(SocketMessage("PlayerJoined", user = user, room = null, story = null))
                    user.session.sendSerialized(SocketMessage("PlayerJoined", user = otherUser, room = null, story = null))
                }
            }
        }

        suspend fun quitRoom(session: DefaultWebSocketServerSession) {
            val user = UserService.getBySession(session)
            if (user == null) {
                return
            }

            val roomId = user.roomId
            val room = rooms[roomId]

            if (room == null) {
                return
            }
            room.users -= user

            if (room.users.isEmpty()) {
                rooms.remove(roomId)
            }

            for (u in room.users) {
                u.session.sendSerialized(SocketMessage("PlayerLeft", user = user, room = null, story = null))
            }
        }

        suspend fun updateRoom(session: DefaultWebSocketServerSession, updatedRoom: Room) {
            val user = UserService.getBySession(session) ?: return
            val room = rooms[user.roomId] ?: return

            if (room.administrator.id != user.id) {
                session.sendSerialized(SocketMessage("Only admin can modify the room.", user = null, room = null, story = null))
                return
            }

            room.users = updatedRoom.users ?: room.users
            room.administrator = updatedRoom.administrator ?: room.administrator
            room.stories = updatedRoom.stories ?: room.stories

            for (otherUser in room.users) {
                otherUser.session.sendSerialized(SocketMessage("RoomUpdated", user = null, room = room, story = null))
            }
        }

        suspend fun startNewStory(session: DefaultWebSocketServerSession, story : Story) {
            val user = UserService.getBySession(session) ?: return
            val room = rooms[user.roomId] ?: return

            if (room.administrator.id != user.id) {
                session.sendSerialized(SocketMessage("Only admin can start a new round.", user = null, room = null, story = null))
                return
            }

            room.stories += story

            for (u in room.users) {
                u.card = null
                u.session.sendSerialized(SocketMessage("NewRoundStarted", user = null, room = null, story = null))
            }
        }
    }
}
