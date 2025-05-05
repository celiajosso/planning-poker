package com.example.models

import kotlinx.serialization.Serializable

@Serializable
open class Room(
    val id: String,
    val name: String,
    @Transient var users: List<User> = listOf(),
    @Transient var administrator: User,
    @Transient var stories: List<Story> = listOf()
)
