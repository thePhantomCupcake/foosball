package com.epiuse.labs.epifoos.security

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.securityRoutes() {

    routing {

        post("/sign-in") {
            val signInRequest = call.receive<SignInRequest>()
            val tokens = SecurityService.signIn(signInRequest)

            call.respond(tokens)
        }

        post("/sign-up") {
            val addPlayerRequest = call.receive<SignUpRequest>()

            SecurityService.signUp(addPlayerRequest)

            call.respond(HttpStatusCode.OK)
        }

        post("/confirm-sign-up") {
            val confirmSignUpRequest = call.receive<ConfirmSignUpRequest>()

            call.respond(SecurityService.confirmSignUp(confirmSignUpRequest))
        }
    }
}