package com.example.models

import io.ktor.server.websocket.*
import kotlinx.serialization.Serializable

@Serializable
open class User(
    val id: String,
    @Transient
    val session: DefaultWebSocketServerSession,
    val username: String,
    val role: Role = Role.Player,
    val roomId: String,
    val card : String?
)
