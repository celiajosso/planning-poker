package com.example.services

import com.example.models.StoryDTO
import com.example.models.toRoomDTO
import io.ktor.server.websocket.*
import java.util.*

class StoryService(/*private val db: MongoDatabase*/) {
    //private val stories = db.getCollection("stories", Story::class.java)
    companion object {

        suspend fun createStory(socket: DefaultWebSocketServerSession, story: StoryDTO) {
            val id = UUID.randomUUID().toString()
            story.id = id
            val room = RoomService.getById(story.roomId) ?: return
            val dto = room.toRoomDTO()

            dto.stories += story

            RoomService.updateRoom(socket, dto)
        }

        suspend fun updateStory(socket: DefaultWebSocketServerSession, story: StoryDTO) {
            val room = RoomService.getById(story.roomId) ?: return
            val dto = room.toRoomDTO()

            dto.stories.find { it.id == story.id }?.apply {
                this.title = story.title
                this.description = story.description
                this.finalEstimate = story.finalEstimate
            }

            RoomService.updateRoom(socket, dto)
        }

        suspend fun selectStory(socket: DefaultWebSocketServerSession, story: StoryDTO) {
            val room = RoomService.getById(story.roomId) ?: return
            val dto = room.toRoomDTO()

            val selected = dto.stories.find { it.id == story.id } ?: return
            dto.storySelected = selected

            RoomService.updateRoom(socket, dto)
        }

        suspend fun newRound(socket: DefaultWebSocketServerSession, story: StoryDTO) {
            val room = RoomService.getById(story.roomId) ?: return
            val dto = room.toRoomDTO()

            // TODO


            RoomService.updateRoom(socket, dto)
        }

        suspend fun save(socket: DefaultWebSocketServerSession, story: StoryDTO) {
            // TODO


            RoomService.updateRoom(socket, dto)
        }

        suspend fun unselectStory(socket: DefaultWebSocketServerSession, story: StoryDTO) {
            val room = RoomService.getById(story.roomId) ?: return
            val dto = room.toRoomDTO()

            dto.storySelected = null

            RoomService.updateRoom(socket, dto)
        }

        suspend fun deleteStory(socket: DefaultWebSocketServerSession, story: StoryDTO) {
            val room = RoomService.getById(story.roomId) ?: return
            val dto = room.toRoomDTO()

            val selected = dto.stories.find { it.id == story.id } ?: return
            dto.stories -= selected

            RoomService.updateRoom(socket, dto)
        }

    }

}
