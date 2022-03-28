package com.epiuse.labs.epifoos.plugins

import com.epiuse.labs.epifoos.game.gameRoutes
import com.epiuse.labs.epifoos.match.matchRoutes
import com.epiuse.labs.epifoos.player.playerRoutes
import com.epiuse.labs.epifoos.security.securityRoutes
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        gameRoutes()
        matchRoutes()
        playerRoutes()
        securityRoutes()
        install(StatusPages) {
            exception<AuthenticationException> { call, cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { call, cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }
        static("/") {
            resources("static")
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
