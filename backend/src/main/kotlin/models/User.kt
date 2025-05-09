package com.example.models

import io.ktor.server.websocket.*
import kotlinx.serialization.Serializable

@Serializable
open class User(
    val id: String,
    @Transient
    val session: DefaultWebSocketServerSession,
    var username: String,
    var role: Role,
    val roomId: String,
    var card: Int
)

@Serializable
data class UserDTO(
    val id: String,
    var username: String,
    var role: Role,
    val roomId: String,
    var card: Int
)

fun User.toUserDTO(): UserDTO {
    return UserDTO(id, username, role, roomId, card)
}