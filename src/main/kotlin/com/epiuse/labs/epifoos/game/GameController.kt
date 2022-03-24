package com.epiuse.labs.epifoos.game

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.Application
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.gameRoutes() {

    routing {
        get(GAMES_ROOT) {
            val games = mutableListOf<GameSummary>()
            transaction {
                Game.all().forEach { games.add(it.toSummary()) }
            }
            call.respond(games)
        }

        get("$GAMES_ROOT/{gameId}") {
            val gameId = call.parameters["gameId"]

            var gameSummary: GameSummary? = null
            transaction {
                gameSummary = Game.findById(gameId!!.toInt())?.toSummary()
            }

            if (gameSummary != null) {
                call.respond(gameSummary!!)
            } else {
                call.respond(HttpStatusCode.NotFound, "Game not found")
            }
        }
    }

}

const val GAMES_ROOT = "/api/games"