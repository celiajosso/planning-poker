package com.example

import RoomService
import com.example.models.SocketMessage
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
                            "join" -> {
                                RoomService.joinRoom(UserService.create(this, message.user!!))
                            }
                            "createRoom" -> {
                                RoomService.create(this, message.user!!, message.room!!)
                            }
//                            "createStory" -> {
//                                StoryService.create(this, message.story!!)
//                            }
                            "quit" -> {
                                RoomService.quitRoom(this)
                            }
                            "updateUser" -> {
                                UserService.updateUser(this, message.user!!)
                            }
                            "updateRoom" -> {
                                RoomService.updateRoom(this, message.room!!)
                            }
//                            "updateStory" -> {
//                                StoryService.updateStory(this, message.story!!)
//                            }
                            "newStory" -> {
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
