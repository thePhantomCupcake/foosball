package com.epiuse.labs.epifoos.security.cognito

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder
import com.amazonaws.services.cognitoidp.model.*
import com.auth0.jwt.JWT
import com.epiuse.labs.epifoos.security.ConfirmSignUpRequest
import com.epiuse.labs.epifoos.security.SignInRequest
import com.epiuse.labs.epifoos.security.SignUpRequest

class CognitoService {

    fun signUp(signUpRequest: SignUpRequest): SignUpResult {
        val cognitoRequest = com.amazonaws.services.cognitoidp.model.SignUpRequest()
            .withUsername(signUpRequest.email)
            .withUserAttributes(
                AttributeType()
                    .withName("given_name")
                    .withValue(signUpRequest.firstName),
                AttributeType()
                    .withName("family_name")
                    .withValue(signUpRequest.lastName)
            )
            .withPassword(signUpRequest.password)

        return COGNITO_CLIENT.signUp(cognitoRequest)
    }

    fun confirmSignUp(confirmSignUpRequest: ConfirmSignUpRequest): ConfirmSignUpResult? {

        val cognitoSignUpConfirmation = com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest()
            .withClientId(APP_CLIENT_ID)
            .withUsername(confirmSignUpRequest.email)
            .withConfirmationCode(confirmSignUpRequest.confirmationCode)

        return COGNITO_CLIENT.confirmSignUp(cognitoSignUpConfirmation)
    }

    fun signIn(signInRequest: SignInRequest): AuthenticationResultType {
        val authParams = hashMapOf(
            "USERNAME" to signInRequest.email,
            "PASSWORD" to signInRequest.password
        )

        val authRequest = AdminInitiateAuthRequest()
            .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
            .withUserPoolId(USER_POOL)
            .withClientId(APP_CLIENT_ID)
            .withAuthParameters(authParams)

        return COGNITO_CLIENT.adminInitiateAuth(authRequest).authenticationResult
    }

    companion object {

        private val AWS_REGION: String = "eu-west-2"
        private val APP_CLIENT_ID: String = "7s8fs59vvtr1kfl2cup6eutght"
        private val USER_POOL: String = "eu-west-2_pzQSusRXg"

        private val APP_CLIENT_ACCESS_KEY: String = "AKIARU27BXSJZIOBCXFX"
        private val APP_CLIENT_SECRET_KEY: String = "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"

        private val COGNITO_CLIENT = AWSCognitoIdentityProviderClientBuilder.standard()
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(APP_CLIENT_ACCESS_KEY, APP_CLIENT_SECRET_KEY)
                )
            )
            .withRegion(AWS_REGION)
            .build()
    }
}