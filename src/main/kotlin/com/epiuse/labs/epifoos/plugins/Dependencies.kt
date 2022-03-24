package com.epiuse.labs.epifoos.plugins

import io.ktor.server.application.*

fun Application.configureDependencies() {

    install(KoinPlugin) {
        modules(
        )
    }

}
