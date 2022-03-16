package com.epiuse.labs.epifoos.plugins

import com.epiuse.labs.epifoos.match.matchControllerModule
import com.epiuse.labs.epifoos.match.matchServiceModule
import io.ktor.server.application.*

fun Application.configureDependencies() {

    install(KoinPlugin) {
        modules(
            matchControllerModule,
            matchServiceModule
        )
    }

}
