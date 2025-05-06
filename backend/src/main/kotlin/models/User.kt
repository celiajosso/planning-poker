package com.example.models

import io.ktor.server.websocket.*
import kotlinx.serialization.Serializable

@Serializable
open class User(
    val id: String,
    @Transient
    val session: DefaultWebSocketServerSession,
    var username: String,
    var role: Role = Role.Player,
    val roomId: String,
    var card: String?
)
