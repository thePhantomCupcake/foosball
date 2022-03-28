package com.epiuse.labs.epifoos

import com.epiuse.labs.epifoos.game.Games
import com.epiuse.labs.epifoos.match.Matches
import com.epiuse.labs.epifoos.player.PlayerElos
import com.epiuse.labs.epifoos.player.Players
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.epiuse.labs.epifoos.plugins.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/epifoos?stringtype=unspecified&reWriteBatchedInserts=true",
        driver = "org.postgresql.Driver",
        user = "epifoos",
        password = "lastPass"
    )

    transaction {
        SchemaUtils.create(
            Players,
            Games,
            Matches,
            PlayerElos
        )
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureHTTP()
        configureAdministration()
        configureSecurity()
        configureSerialization()
        configureRouting()
        configureMonitoring()
    }.start(wait = true)
}
