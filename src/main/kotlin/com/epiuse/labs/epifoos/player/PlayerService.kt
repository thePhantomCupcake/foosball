package com.epiuse.labs.epifoos.player

import com.epiuse.labs.epifoos.security.SecurityService
import com.epiuse.labs.epifoos.security.SignUpRequest
import org.koin.dsl.module

class PlayerService(private val securityService: SecurityService) {

    fun signUserUp(signUpRequest: SignUpRequest) {
        securityService.signUp(signUpRequest)
    }
}

val securityServiceModule = module {
    single { PlayerService(get()) }
    single { SecurityService(get()) }
}