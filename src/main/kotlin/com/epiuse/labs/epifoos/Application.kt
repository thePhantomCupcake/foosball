package com.epiuse.labs.epifoos

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.epiuse.labs.epifoos.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureHTTP()
        configureAdministration()
        configureSecurity()
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureDependencies()
    }.start(wait = true)
}
