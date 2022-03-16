package com.epiuse.labs.epifoos.match

import org.koin.dsl.module

class MatchController(val matchService: MatchService) {
}

val matchControllerModule = module(createdAtStart = true) {
    single { MatchController(get()) }
}