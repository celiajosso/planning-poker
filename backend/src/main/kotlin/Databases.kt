package com.example

import com.example.models.DB_User
import com.example.services.DatabaseService
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.Document
import java.security.MessageDigest

@OptIn(ExperimentalStdlibApi::class)
fun String.sha512(): String {
    val md = MessageDigest.getInstance("SHA-512")
    val digest = md.digest(this.toByteArray())
    return digest.toHexString()
}

fun Application.configureDatabases() {
    DatabaseService.init(connectToMongoDB())
    var database = DatabaseService.getDatabase()
    routing {
        route("/api/login") {
            post {
                val user = call.receive<DB_User>()

                val collection = database.getCollection("users")
                val userInDb = collection.find(Filters.eq("username", user.username)).first()

                userInDb?.let {
                    if (it["password"] == user.password.sha512()) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(HttpStatusCode.Unauthorized)
                    }
                    return@post
                }
            }
        }
        route("/api/register") {
            post {
                val user = call.receive<DB_User>()

                val collection = database.getCollection("users")
                val userInDb = collection.find(Filters.eq("username", user.username)).first()

                userInDb?.let {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }
                val document = Document()
                    .append("username", user.username)
                    .append("password", user.password.sha512())
                call.respond(HttpStatusCode.Created)
                collection.insertOne(document)
            }
        }
        route("/api/history") {
            get {
                val username = call.request.queryParameters["username"]
                if (username == null) {
                    call.respond(HttpStatusCode.BadRequest, "Missing username")
                    return@get
                }
                val collection = database.getCollection("stories")
                val storiesInDb = collection.find(Filters.`in`("participants", username)).toList()
                call.respond(
                    storiesInDb.map {
                        com.example.models.DB_Story(
                            title = it["title"].toString(),
                            description = it["description"].toString(),
                            votes = (it["votes"] as Document).entries.associate { entry ->
                                entry.key to (entry.value as List<Int>)
                            } as HashMap<String, List<Int>>,
                            participants = it["participants"] as ArrayList<String>,
                            timestamp = it["timestamp"] as Long
                        )
                    }
                )
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
