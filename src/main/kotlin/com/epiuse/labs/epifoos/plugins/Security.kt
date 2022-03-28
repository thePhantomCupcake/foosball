package com.epiuse.labs.epifoos.plugins

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.epiuse.labs.epifoos.security.cognito.CognitoService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


fun Application.configureSecurity() {

    install(Authentication) {
        jwt {
            val jwtAudience = CognitoService.APP_CLIENT_ID

            realm = "Access to 'EPIFoos'"

            val provider: JwkProvider = UrlJwkProvider(
                "https://cognito-idp.eu-west-2.amazonaws.com/eu-west-2_pzQSusRXg/.well-known/jwks.json"
            )
            verifier(provider) {
                acceptLeeway(3)
            }

            challenge { _, _ ->
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
