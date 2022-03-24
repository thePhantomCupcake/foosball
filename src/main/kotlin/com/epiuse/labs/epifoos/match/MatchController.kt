package com.epiuse.labs.epifoos.match

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.matchRoutes() {

    routing {

        get(MATCHES_ROOT) {
            call.respond(Match.all())
        }

        get("$MATCHES_ROOT/{matchId}") {
            val matchId = call.parameters["matchId"]

            val match = Match.findById(matchId!!.toInt())

            if (match != null) {
                call.respond(it)
            } else {
                call.respond(HttpStatusCode.NotFound, "Match not found")
            }
        }

        post(MATCHES_ROOT) {
            val captureMatchRequest = call.receive<CaptureMatchRequest>()
            val newMatch = MatchService.captureMatch(captureMatchRequest)

            call.respond(newMatch)
        }
    }
}

const val MATCHES_ROOT = "/api/matches"