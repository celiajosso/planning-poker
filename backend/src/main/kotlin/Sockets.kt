package com.example

import com.example.models.SocketMessage
import com.example.services.RoomService
import com.example.services.StoryService
import com.example.services.UserService
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
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
                            "RoomCreate" -> {
                                RoomService.create(this, message.user!!, message.room!!)
                            }

                            "RoomJoin" -> {
                                RoomService.joinRoom(this, message.user!!)
                            }

                            "RoomUpdate" -> {
                                RoomService.updateRoom(this, message.room!!)
                            }

                            "RoomQuit" -> {
                                RoomService.quitRoom(this)
                            }

                            "StoryCreate" -> {
                                StoryService.createStory(this, message.story!!)
                            }

                            "StoryUpdate" -> {
                                StoryService.updateStory(this, message.story!!)
                            }

                            "StoryNewRound" -> {
                                StoryService.newRound(this, message.story!!)
                            }

                            "StorySave" -> {
                                StoryService.newRound(this, message.story!!)
                            }

                            "StorySelect" -> {
                                StoryService.selectStory(this, message.story!!)
                            }

                            "StoryUnselect" -> {
                                StoryService.unselectStory(this, message.story!!)
                            }

                            "StoryDelete" -> {
                                StoryService.deleteStory(this, message.story!!)
                            }

                            "UserUpdate" -> {
                                UserService.updateUser(this, message.user!!)
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
