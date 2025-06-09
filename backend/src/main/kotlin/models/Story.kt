package com.example.models

import kotlinx.serialization.Serializable

@Serializable
open class Story(
    val id: String,
    var title: String,
    var description: String,
    var finalEstimate: Float = 0.0f,
    val roomId: String,
    var votes: HashMap<String, List<Int>>,
    var isSaved: Boolean,
)

@Serializable
data class StoryDTO(
    var id: String,
    var title: String,
    var description: String,
    var finalEstimate: Float = 0.0f,
    val roomId: String,
    var votes: HashMap<String, List<Int>>,
    var isSaved: Boolean,
)

fun Story.toStoryDTO(): StoryDTO {
    return StoryDTO(id, title, description, finalEstimate, roomId, votes, isSaved)
}

fun StoryDTO.toStory(): Story {
    return Story(
        id, title, description, finalEstimate, roomId, votes, isSaved
    )
}
