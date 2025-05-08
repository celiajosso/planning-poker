package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class SocketMessage(val type: String, val user: UserDTO?, val room: RoomDTO?, val story: Story?)