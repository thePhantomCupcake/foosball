package com.epiuse.labs.epifoos

import io.ktor.server.plugins.*
import io.ktor.http.content.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*
import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.serialization.kotlinx.json.*
import io.micrometer.prometheus.*
import io.ktor.server.metrics.micrometer.*
import org.slf4j.event.*
import io.ktor.server.request.*
import io.ktor.server.webjars.*
import java.time.*
import io.ktor.server.http.content.*
import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.locations.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.server.sessions.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.epiuse.labs.epifoos.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}