package services

import User
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import org.bson.types.ObjectId

class UserService(private val db: MongoDatabase) {
    private val users = db.getCollection("users", User::class.java)

    fun create(user: User): String {
        users.insertOne(user)
        return user.id ?: ""
    }

    fun getAll(): List<User> = users.find().toList()

    fun getById(id: String): User? =
        users.find(eq("_id", ObjectId(id))).firstOrNull()

    fun getByRoomId(roomId: String): List<User> =
        users.find(eq("roomId", roomId)).toList()

    fun update(id: String, updatedUser: User): Boolean {
        val result = users.replaceOne(eq("_id", ObjectId(id)), updatedUser)
        return result.modifiedCount > 0
    }

    fun delete(id: String): Boolean {
        val result = users.deleteOne(eq("_id", ObjectId(id)))
        return result.deletedCount > 0
    }

}
