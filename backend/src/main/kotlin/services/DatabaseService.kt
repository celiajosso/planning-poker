package com.example.services

import com.mongodb.client.MongoDatabase

class DatabaseService {
    companion object {
        private var database: List<MongoDatabase> = listOf()

        fun init(db: MongoDatabase) {
            database += db
            getDatabase().createCollection("users")
            getDatabase().createCollection("stories")
        }

        fun getDatabase(): MongoDatabase {
            return database[0]
        }
    }
}
