package com.example.models
import kotlinx.serialization.Serializable
@Serializable
data class SocketMessage(open val type: String, val user: User?, val room: Room?)