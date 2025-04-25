package com.example

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import com.mongodb.client.MongoDatabase
import models.User
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.sendSerialized

@Serializable
data class PlayerInfo(val id: String, val name: String, val card: Int)

@Serializable
data class SocketMessage(val type: String, val playerInfo: PlayerInfo, val roomId: String)


fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        val connections = mutableSetOf<DefaultWebSocketServerSession>()
        val players = HashMap<DefaultWebSocketServerSession, PlayerInfo>()

        webSocket("/poker") {
            connections += this
            try {
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        val message = Json.decodeFromString<SocketMessage>(text)
                        println("Received: $message")

                        players[this] = message.playerInfo

                        if (message.type == "join") {
                            val roomId = message.roomId ?: return@forEach
                            val user = User(id = message.playerInfo.id, username = message.playerInfo.name)
                            roomService.joinRoom(roomId, user, this)

                            players[this] = message.playerInfo

                            for ((conn, player) in players) {
                                if (conn != this) {
                                    println("Debug: $message")
                                    val existingPlayerMsg = SocketMessage("join", player, roomId)
                                    conn.send(Json.encodeToString(existingPlayerMsg))
                                }
                            }
                        }

                        if (message.type == "quit") {
                            players.remove(this)
                            val userId = message.playerInfo.id
                            roomService.quitRoom(message.roomId ?: return@forEach, userId)

                            for ((conn, player) in players) {
                                val quitMessage = SocketMessage("quit", player, message.roomId)
                                conn.send(Json.encodeToString(quitMessage))
                            }

                            this.close()
                        }

                        //println("Players in room: ${players.values.map { it.name }}")

                        // Broadcast to others
                        for (conn in connections) {
                            conn.send(text)
                        }
                    }
                }
            } finally {
                val oldPlayer = players[this]
                players.remove(this)
                for ((conn, player) in players) {
                    val existingPlayerMsg = oldPlayer?.let { SocketMessage("quit", it) }
                    conn.send(Json.encodeToString(existingPlayerMsg))
                }
                connections -= this
            }
        }

        webSocket("/ws") { // websocketSession
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    outgoing.send(Frame.Text("YOU SAID: $text"))
                    if (text.equals("bye", ignoreCase = true)) {
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                    }
                }
            }
        }
    }
}
