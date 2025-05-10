package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        staticRootFolder = File("dist")
        staticFiles("/", File("dist"))

        get("/{page}") {
            val page = call.parameters["page"]
            val file = File("dist/$page.html")
            val file2 = File("dist/$page")
            if (file.exists()) {
                call.respondFile(file)
            } else if (file2.exists()) {
                call.respondFile(file2)
            } else {
                call.respond(HttpStatusCode.NotFound, "Page not found")
            }
        }

        staticFiles("/docs", File("docs"))
    }
}