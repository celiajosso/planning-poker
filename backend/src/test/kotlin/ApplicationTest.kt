package com.example

import io.ktor.server.testing.*
import kotlin.test.Test

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        /*
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }*/
    }

}
