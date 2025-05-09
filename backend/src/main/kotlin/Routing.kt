package com.example

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.http.content.default
import io.ktor.server.http.content.staticFiles
import io.ktor.server.http.content.staticRootFolder
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File

fun Application.configureRouting() {
    routing {
        staticRootFolder = File("dist")
        staticFiles( "/", File("dist"))

        get("/{page}") {
            val page = call.parameters["page"]
            val file = File("dist/$page.html")
            val file2 = File("dist/$page")
            if (file.exists()) {
                call.respondFile(file)
            }
            else if (file2.exists()) {
                call.respondFile(file2)
            } else {
                call.respond(HttpStatusCode.NotFound, "Page not found")
            }
        }

        staticFiles("/docs", File("docs"))
    }
}