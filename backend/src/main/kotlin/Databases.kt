package com.example

import Room
import RoomService
import Story
import User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import services.StoryService
import services.UserService
import java.time.Duration
import kotlin.time.Duration.Companion.seconds

fun Application.configureDatabases() {
    val mongoDatabase = connectToMongoDB()
    val roomService = RoomService(mongoDatabase)
    val storyService = StoryService(mongoDatabase)
    val userService = UserService(mongoDatabase)

    routing {
        route("/rooms") {
            get {
                call.respond(roomService.getAll())
            }
            get("{id}") {
                val id = call.parameters["id"]
                val room = id?.let { roomService.getById(it) }
                if (room != null) call.respond(room)
                else call.respond(HttpStatusCode.NotFound)
            }
            post {
                val room = call.receive<Room>()
                val id = roomService.create(room)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            }
            put("{id}") {
                val id = call.parameters["id"]
                val updatedRoom = call.receive<Room>()
                if (id != null && roomService.update(id, updatedRoom)) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            delete("{id}") {
                val id = call.parameters["id"]
                if (id != null && roomService.delete(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/stories") {
            get {
                call.respond(storyService.getAll())
            }
            get("{id}") {
                val id = call.parameters["id"]
                val story = id?.let { storyService.getById(it) }
                if (story != null) call.respond(story)
                else call.respond(HttpStatusCode.NotFound)
            }
            post {
                val story = call.receive<Story>()
                val id = storyService.create(story)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            }
            put("{id}") {
                val id = call.parameters["id"]
                val updatedStory = call.receive<Story>()
                if (id != null && storyService.update(id, updatedStory)) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            delete("{id}") {
                val id = call.parameters["id"]
                if (id != null && storyService.delete(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/users") {
            get {
                call.respond(userService.getAll())
            }
            get("{id}") {
                val id = call.parameters["id"]
                val user = id?.let { userService.getById(it) }
                if (user != null) call.respond(user)
                else call.respond(HttpStatusCode.NotFound)
            }
            post {
                val user = call.receive<User>()
                val id = userService.create(user)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            }
            put("{id}") {
                val id = call.parameters["id"]
                val updatedUser = call.receive<User>()
                if (id != null && userService.update(id, updatedUser)) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            delete("{id}") {
                val id = call.parameters["id"]
                if (id != null && userService.delete(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}

/**
 * Establishes connection with a MongoDB database.
 *
 * The following configuration properties (in application.yaml/application.conf) can be specified:
 * * `db.mongo.user` username for your database
 * * `db.mongo.password` password for the user
 * * `db.mongo.host` host that will be used for the database connection
 * * `db.mongo.port` port that will be used for the database connection
 * * `db.mongo.maxPoolSize` maximum number of connections to a MongoDB server
 * * `db.mongo.database.name` name of the database
 *
 * IMPORTANT NOTE: in order to make MongoDB connection working, you have to start a MongoDB server first.
 * See the instructions here: https://www.mongodb.com/docs/manual/administration/install-community/
 * all the paramaters above
 *
 * @returns [MongoDatabase] instance
 * */
fun Application.connectToMongoDB(): MongoDatabase {
    val user = System.getenv("DB_MONGO_USER")
    val password = System.getenv("DB_MONGO_PASSWORD")
    val host = System.getenv("DB_MONGO_HOST") ?: "127.0.0.1"
    val port = System.getenv("DB_MONGO_PORT") ?: "27017"
    val maxPoolSize = System.getenv("DB_MONGO_MAX_POOL_SIZE")?.toInt() ?: 20
    val databaseName = System.getenv("DB_MONGO_DB") ?: "myDatabase"
    val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
    val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"

    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase(databaseName)

    monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}
