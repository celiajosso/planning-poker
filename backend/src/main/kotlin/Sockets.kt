package com.example

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

@Serializable
data class PlayerInfo(val id: String, val name: String, val card: Int)

@Serializable
data class SocketMessage(val type: String, val playerInfo: PlayerInfo)


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
                            for ((conn, player) in players) {
                                if (conn != this) {
                                    println("Debug: $message")
                                    val existingPlayerMsg = SocketMessage("join", player)
                                    this.send(Json.encodeToString(SocketMessage.serializer(), existingPlayerMsg))
                                }
                            }
                        }

                        if (message.type == "quit") {
                            players.remove(this)
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
