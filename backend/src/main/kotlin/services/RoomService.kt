import com.example.models.*
import com.mongodb.client.MongoDatabase
import io.ktor.server.websocket.*
import kotlinx.serialization.json.JsonNull
import services.StoryService
import services.UserService
import java.util.*

class RoomService {
    companion object {
        private val rooms = mutableMapOf<String, Room>()
        private lateinit var storyService: StoryService

        fun init(db: MongoDatabase) {
            storyService = StoryService(db)
        }

        suspend fun create(session: DefaultWebSocketServerSession, reqUser: UserDTO, reqRoom: RoomDTO) {
            val id = UUID.randomUUID().toString()
            val user = UserService.create(session, reqUser, id, Role.Administrator)
            val room = Room(id, reqRoom.name, administrator = user)
            rooms[id] = room

            room.users += user

            val data = SocketMessage("RoomCreated", room = room.toRoomDTO(), user = user.toUserDTO(), story = null)
            session.sendSerialized(data)
        }

        fun getById(roomId: String): Room? {
            return rooms[roomId]
        }

        suspend fun joinRoom(session: DefaultWebSocketServerSession, userDTO: UserDTO) {
            val room = rooms.getValue(userDTO.roomId)
            val user = UserService.create(session, userDTO, userDTO.roomId, Role.Player)
            room.users += user

            session.sendSerialized(SocketMessage("RoomJoined", room = room.toRoomDTO(), user = user.toUserDTO(), story = null))

            for (otherUser in room.users) {
                if (session != otherUser.session) {
                    otherUser.session.sendSerialized(
                        SocketMessage(
                            "UserJoined",
                            user = user.toUserDTO(),
                            room = null,
                            story = null
                        )
                    )
                    session.sendSerialized(
                        SocketMessage(
                            "UserJoined",
                            user = otherUser.toUserDTO(),
                            room = null,
                            story = null
                        )
                    )
                }
            }
        }

        suspend fun quitRoom(session: DefaultWebSocketServerSession) {
            val user = UserService.getBySession(session)
            if (user == null) {
                return
            }

            val roomId = user.roomId
            val room = rooms[roomId] ?: return

            for (u in room.users) {
                u.session.sendSerialized(
                    SocketMessage(
                        "UserLeft",
                        user = user.toUserDTO(),
                        room = null,
                        story = null
                    )
                )
            }

            room.users -= user

            if (room.users.isEmpty()) {
                rooms.remove(roomId)
            }
        }
        suspend fun updateRoom(session: DefaultWebSocketServerSession, updatedRoom: RoomDTO) {
            val user = UserService.getBySession(session) ?: return
            val room = rooms[user.roomId] ?: return

            if (room.administrator.id != user.id) {
                session.sendSerialized(
                    SocketMessage(
                        "Only admin can modify the room.",
                        user = null,
                        room = null,
                        story = null
                    )
                )
                return
            }

            //room.users = updatedRoom.users ?: room.users
            //room.administrator = updatedRoom.administrator ?: room.administrator
            //room.stories = updatedRoom.stories ?: room.stories

            for (otherUser in room.users) {
                otherUser.session.sendSerialized(SocketMessage("RoomUpdated", user = null, room = room.toRoomDTO(), story = null))
            }
        }

        suspend fun startNewStory(session: DefaultWebSocketServerSession, story: Story) {
            val user = UserService.getBySession(session) ?: return
            val room = rooms[user.roomId] ?: return

            if (room.administrator.id != user.id) {
                session.sendSerialized(
                    SocketMessage(
                        "Only admin can start a new round.",
                        user = null,
                        room = null,
                        story = null
                    )
                )
                return
            }

            room.stories += story

            for (u in room.users) {
                u.card = -1
                u.session.sendSerialized(SocketMessage("NewRoundStarted", user = null, room = null, story = null))
            }
        }
    }
}
