package com.epiuse.labs.epifoos.security

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.epiuse.labs.epifoos.security.cognito.CognitoService
import org.koin.dsl.module

object SecurityService {

    fun signIn(signInRequest: SignInRequest): DecodedJWT {
        val signInResult = CognitoService.signIn(signInRequest)

        return JWT.decode(signInResult.accessToken)
    }

    fun signUp(signUpRequest: SignUpRequest) {
        CognitoService.signUp(signUpRequest)
    }

    fun confirmSignUp(confirmSignUpRequest: ConfirmSignUpRequest) {
        CognitoService.confirmSignUp(confirmSignUpRequest)
    }
}