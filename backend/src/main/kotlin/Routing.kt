package com.example

import io.ktor.server.application.*
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.routing
import java.io.File

fun Application.configureRouting() {
    routing {
        staticFiles("/", File("dist"))
    }
}
