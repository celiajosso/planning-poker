package com.example.models

import kotlinx.serialization.Serializable

@Serializable
open class Story(
    val id: String,
    var title: String,
    var description: String,
    var finalEstimate: String,
    val roomId: String,
    var votes: HashMap<String, List<Int>>,
)

@Serializable
data class StoryDTO(
    var id: String,
    var title: String,
    var description: String,
    var finalEstimate: String,
    val roomId: String,
    var votes: HashMap<String, List<Int>>,
)

fun Story.toStoryDTO(): StoryDTO {
    return StoryDTO(id, title, description, finalEstimate, roomId, votes)
}

fun StoryDTO.toStory(): Story {
    return Story(
        id, title, description, finalEstimate, roomId, votes
    )
}
