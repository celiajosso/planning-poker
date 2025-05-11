package services

import com.example.models.Role
import com.example.models.RoomDTO
import com.example.models.SocketMessage
import com.example.models.UserDTO
import com.example.module
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RoomServiceTest {
    @Test
    fun `Test room creation`() {
        testApplication {
            application {
                module()
            }
            val client = createClient {
                install(WebSockets) {
                    contentConverter = KotlinxWebsocketSerializationConverter(Json)
                }
            }

            client.webSocket("/poker") {
                val userDTO = UserDTO("", "Admin", Role.Player, "", 50)
                val roomDTO = RoomDTO("", "Room", null, ArrayList())

                sendSerialized(SocketMessage("RoomCreate", userDTO, roomDTO, null))

                val responseText = (incoming.receive() as Frame.Text).readText()
                val message = Json.decodeFromString<SocketMessage>(responseText)

                assertNotNull(message)
                assertNotNull(message.user)
                assertNotNull(message.room)

                assertEquals("RoomCreated", message.type)

                assertEquals(userDTO.username, message.user.username)
                assertEquals(Role.Administrator, message.user.role)
                assertEquals(-1, message.user.card)
                assertEquals(message.room.id, message.user.roomId)

                assertEquals(roomDTO.name, message.room.name)
            }
        }
    }
}