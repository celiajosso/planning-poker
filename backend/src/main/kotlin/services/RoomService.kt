import com.example.models.Role
import com.example.models.Room
import com.example.models.SocketMessage
import com.example.models.User
import io.ktor.server.websocket.*
import services.UserService
import java.util.*

class RoomService() {
    companion object {
        private val rooms = mutableMapOf<String, Room>()

        suspend fun create(session : DefaultWebSocketServerSession, reqUser: User, reqRoom: Room) {
            val user = UserService.create(session, reqUser, Role.Administrator)

            val id = UUID.randomUUID().toString()
            val room = Room(id, reqRoom.name, administrator = user)
            rooms[id] = room

            session.sendSerialized(SocketMessage("RoomCreated", room=room, user=user))
        }

        suspend fun joinRoom(user: User) {
            val room = rooms.getValue(user.roomId)
            room.users += user

            user.session.sendSerialized("roomJoined")

            for (otherUser in room.users) {
                if (user.session != otherUser.session) {
                    otherUser.session.sendSerialized(SocketMessage("PlayerJoined", user=user, room=null))
                    user.session.sendSerialized(SocketMessage("PlayerJoined", user=otherUser, room=null))
                }
            }
        }

        fun quitRoom(session: DefaultWebSocketServerSession) {
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
        }
    }
}
