package com.example.models

import kotlinx.serialization.Serializable

open class Room(
    val id: String,
    val name: String,
    var storySelected: Story?,
    @Transient var users: List<User> = listOf(),
    @Transient var administrator: User,
    var stories: List<Story> = listOf()
)

@Serializable
data class RoomDTO(
    val id: String,
    val name: String,
    var storySelected: StoryDTO?,
    var stories: List<StoryDTO> = listOf()
)

fun Room.toRoomDTO(): RoomDTO {
    return RoomDTO(id, name, storySelected?.toStoryDTO(), stories.map { it.toStoryDTO() })
}
