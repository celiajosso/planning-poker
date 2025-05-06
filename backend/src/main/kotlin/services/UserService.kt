package services

import com.example.models.Role
import com.example.models.User
import com.example.models.SocketMessage
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.*

class UserService() {
    companion object {

        private val usersById = mutableMapOf<String, User>()
        private val usersBySession = mutableMapOf<DefaultWebSocketServerSession, User>()

        fun create(session: DefaultWebSocketServerSession, reqUser:User, role: Role = Role.Player): User {
            val id = UUID.randomUUID().toString()
            val user = User(id, session, reqUser.username, role, reqUser.roomId, card = null)
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

            deleteSession?.session?.close()

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

        suspend fun updateUser(session: DefaultWebSocketServerSession, updatedUser: User) {
            val user = getBySession(session) ?: return

            user.username = updatedUser.username ?: user.username
            user.role = updatedUser.role ?: user.role
            user.card = updatedUser.card ?: user.card

            val room = RoomService.getById(user.roomId) ?: return
            for (otherUser in room.users) {
                otherUser.session.sendSerialized(SocketMessage("UserUpdated", user = user, room = null, story = null))
            }
        }

    }
}
