package com.epiuse.labs.epifoos.player

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.playerRoutes() {

    routing {
        authenticate {
            get(PLAYER_ROOT) {
                call.respond(Player.all())
            }

            get("$PLAYER_ROOT/{playerId}") {
                val playerId = call.parameters["playerId"]

                val player = Player.findById(playerId!!.toString())?.toSummary()

                if (player != null) {
                    call.respond(it)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Player not found")
                }
            }
        }
    }
}

const val PLAYER_ROOT = "/api/players"