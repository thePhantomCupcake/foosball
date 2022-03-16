package com.epiuse.labs.epifoos.match

import org.koin.dsl.module

class MatchService(val matchRepository: MatchRepository) {


}

val matchServiceModule = module(createdAtStart = true) {
    single { MatchService(get()) }
    single { MatchRepository() }
}