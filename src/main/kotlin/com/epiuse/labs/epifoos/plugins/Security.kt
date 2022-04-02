package com.epiuse.labs.epifoos.plugins

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.UrlJwkProvider
import com.epiuse.labs.epifoos.security.cognito.CognitoService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


fun Application.configureSecurity() {

    install(Authentication) {
        jwt {
            realm = "Access to 'EPIFoos'"

            val issuer = "https://cognito-idp.eu-west-2.amazonaws.com/eu-west-2_xJzoo0rjB"
            val provider: JwkProvider = UrlJwkProvider(
                "https://cognito-idp.eu-west-2.amazonaws.com/eu-west-2_xJzoo0rjB/"
            )
            val jwtAudience = CognitoService.APP_CLIENT_ID
            verifier(provider, issuer) {
                acceptLeeway(3)
            }

            challenge { _, _ ->
                throw AuthenticationException()
            }

            validate { credential ->
                if (credential.payload.getClaim("client_id").asString() == jwtAudience) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
