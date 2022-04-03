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
    val dbHost = System.getenv("DB_HOSTNAME")
    val dbPort = System.getenv("DB_PORT")
    val dbName = System.getenv("DB_NAME")
    val dbUser = System.getenv("DB_USER")
    val dbPass = System.getenv("DB_PASSWORD")

    Database.connect(
        "jdbc:postgresql://$dbHost:$dbPort/$dbName?stringtype=unspecified&reWriteBatchedInserts=true",
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPass
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
