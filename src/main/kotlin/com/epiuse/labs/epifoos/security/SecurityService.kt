package com.epiuse.labs.epifoos.security

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.epiuse.labs.epifoos.security.cognito.CognitoService
import org.koin.dsl.module

class SecurityService(val cognitoService: CognitoService) {

    fun signIn(signInRequest: SignInRequest): DecodedJWT {
        val signInResult = cognitoService.signIn(signInRequest)

        return JWT.decode(signInResult.accessToken)
    }
}

val securityServiceModule = module {
    single { SecurityService(get()) }
    single { CognitoService() }
}