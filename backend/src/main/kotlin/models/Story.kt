package com.example.models

import kotlinx.serialization.Serializable

@Serializable
open class Story(
    val id: String,
    var title: String,
    var description: String,
    var finalEstimate: String,
    val roomId: String,

    
    // val votes: List<Vote> = listOf(),
)

@Serializable
data class Vote(
    val userId: String,
    val value: String
)


@Serializable
data class StoryDTO(
    var id: String,
    var title: String,
    var description: String,
    var finalEstimate: String,
    val roomId: String,
)

fun Story.toStoryDTO(): StoryDTO {
    return StoryDTO(id, title, description, finalEstimate, roomId)
}

fun StoryDTO.toStory(): Story {
    return Story(id, title, description, finalEstimate, roomId)
}
