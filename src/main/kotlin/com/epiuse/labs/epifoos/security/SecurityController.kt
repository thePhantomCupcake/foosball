package com.epiuse.labs.epifoos.security

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.securityRoutes() {

    routing {
        8

        post("/api/sign-in") {
            val signInRequest = call.receive<SignInRequest>()
            val tokens = SecurityService.signIn(signInRequest)

            call.respond(tokens)
        }

        post("/api/sign-up") {
            val addPlayerRequest = call.receive<SignUpRequest>()

            when (val signUpResult = SecurityService.signUp(addPlayerRequest)) {
                is SecurityService.PlayerSignUpResult.Success -> {
                    call.respond(signUpResult.player)
                }
                is SecurityService.PlayerSignUpResult.Failure -> {
                    call.respond(signUpResult.message)
                }
            }
        }

        post("/api/confirm-sign-up") {
            val confirmSignUpRequest = call.receive<ConfirmSignUpRequest>()

            call.respond(SecurityService.confirmSignUp(confirmSignUpRequest))
        }
    }
}