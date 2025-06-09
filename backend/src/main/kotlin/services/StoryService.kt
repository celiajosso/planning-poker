package com.example.services

import com.example.models.StoryDTO
import com.example.models.toRoomDTO
import com.example.models.toUserDTO
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
            val selected = dto.storySelected ?: return

            room.users.forEach { user ->
                var userdto = user.toUserDTO()
                selected.votes[userdto.username] = (selected.votes[userdto.username] ?: mutableListOf()) + userdto.card
                userdto.card = -1
                UserService.updateUser(socket, userdto)
            }

            RoomService.updateRoom(socket, dto)
        }

        suspend fun save(socket: DefaultWebSocketServerSession, story: StoryDTO) {
            val room = RoomService.getById(story.roomId) ?: return
            val dto = room.toRoomDTO()
            val selected = dto.storySelected ?: return
            selected.isSaved = true

            val db = com.example.connectToMongoDB()
            val collection = db.getCollection("stories")
            val document = org.bson.Document()
                .append("id", selected.id)
                .append("title", selected.title)
                .append("description", selected.description)
                .append("finalEstimate", selected.finalEstimate)
                .append("roomId", selected.roomId)
                .append("votes", selected.votes)
                .append("isSaved", selected.isSaved)
            collection.insertOne(document)

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
