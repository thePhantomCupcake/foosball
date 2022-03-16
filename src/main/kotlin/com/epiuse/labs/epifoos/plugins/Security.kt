package com.epiuse.labs.epifoos.plugins

import com.amazonaws.services.cognitoidp.model.UnauthorizedException
import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.server.locations.*
import io.ktor.http.*
import io.ktor.server.sessions.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {

    install(Authentication) {
        jwt {
            val jwtAudience = "http://0.0.0.0:8080/hello"

            realm = "Access to 'hello'"

            val jwtVerifier = JWT
                .require(Algorithm.HMAC256("thisIsASecret"))
                .withAudience(jwtAudience)
                .withIssuer("http://0.0.0.0:8080/")
                .build()
            verifier(
                jwtVerifier
            )

            challenge { _,_ ->
                throw AuthenticationException()
            }

            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

class UserSession(accessToken: String)
