package com.example

import RoomService
import com.example.models.SocketMessage
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import services.StoryService
import services.UserService
import kotlin.time.Duration.Companion.seconds

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    routing {
        webSocket("/poker") {
            try {
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        val message = Json.decodeFromString<SocketMessage>(text)
                        println("Received: $message")

                        when (message.type) {
                            "RoomJoin" -> {
                                RoomService.joinRoom(this, message.user!!)
                            }

                            "RoomCreate" -> {
                                RoomService.create(this, message.user!!, message.room!!)
                            }
                            "StoryCreate" -> {
                                //StoryService.create(this, message.story!!)
                            }
                            "RoomQuit" -> {
                                RoomService.quitRoom(this)
                            }

                            "UserUpdate" -> {
                                UserService.updateUser(this, message.user!!)
                            }

                            "RoomUpdate" -> {
                                RoomService.updateRoom(this, message.room!!)
                            }
                            "StoryUpdate" -> {
                                //StoryService.updateStory(this, message.story!!)
                            }
                            "StoryNew" -> {
                                RoomService.startNewStory(this, message.story!!)
                            }

                            else -> {}
                        }
                    }
                }
            } finally {
                RoomService.quitRoom(this)
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
