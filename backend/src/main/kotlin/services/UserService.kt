package services

import RoomService
import com.example.models.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.*

class UserService() {
    companion object {

        private val usersById = mutableMapOf<String, User>()
        private val usersBySession = mutableMapOf<DefaultWebSocketServerSession, User>()

        fun create(session: DefaultWebSocketServerSession, reqUser: UserDTO, roomId: String, role: Role): User {
            val id = UUID.randomUUID().toString()
            val user = User(id, session, reqUser.username, role, roomId, -1)
            usersById.put(id, user)
            usersBySession.put(session, user)
            return user
        }

        fun getById(id: String): User? {
            return usersById[id]
        }

        fun getBySession(session: DefaultWebSocketServerSession): User? {
            return usersBySession[session]
        }

        suspend fun deleteBySession(session: DefaultWebSocketServerSession): Boolean {
            val deleteSession = usersBySession.remove(session)
            if (deleteSession == null) {
                return false
            }
            val deleteId = usersById.remove(deleteSession.id)

            deleteSession.session.close()

            return deleteId != null
        }

        suspend fun deleteById(id: String): Boolean {
            val deleteId = usersById.remove(id)

            if (deleteId == null) {
                return false
            }
            val deleteSession = usersBySession.remove(deleteId.session)

            deleteSession?.session?.close()

            return deleteSession != null
        }

        suspend fun updateUser(session: DefaultWebSocketServerSession, updatedUser: UserDTO) {
            val user = getBySession(session) ?: return

            user.username = updatedUser.username ?: user.username
            user.role = updatedUser.role ?: user.role
            user.card = updatedUser.card ?: user.card

            val room = RoomService.getById(user.roomId!!) ?: return
            for (otherUser in room.users) {
                otherUser.session.sendSerialized(
                    SocketMessage(
                        "UserUpdated",
                        user = user.toUserDTO(),
                        room = null,
                        story = null
                    )
                )
            }
        }

    }
}
