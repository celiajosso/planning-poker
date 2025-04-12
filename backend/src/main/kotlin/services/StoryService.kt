package services

import Story
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import org.bson.types.ObjectId

class StoryService(private val db: MongoDatabase) {
    private val stories = db.getCollection("stories", Story::class.java)

    fun create(story: Story): String {
        stories.insertOne(story)
        return story.id ?: ""
    }

    fun getAll(): List<Story> = stories.find().toList()

    fun getById(id: String): Story? =
        stories.find(eq("_id", ObjectId(id))).firstOrNull()

    fun getByRoomId(roomId: String): List<Story> =
        stories.find(eq("roomId", roomId)).toList()

    fun update(id: String, updatedStory: Story): Boolean {
        val result = stories.replaceOne(eq("_id", ObjectId(id)), updatedStory)
        return result.modifiedCount > 0
    }

    fun delete(id: String): Boolean {
        val result = stories.deleteOne(eq("_id", ObjectId(id)))
        return result.deletedCount > 0
    }

}
