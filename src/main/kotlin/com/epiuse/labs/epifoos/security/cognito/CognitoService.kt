package com.epiuse.labs.epifoos.security.cognito

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder
import com.amazonaws.services.cognitoidp.model.*
import com.epiuse.labs.epifoos.security.ConfirmSignUpRequest
import com.epiuse.labs.epifoos.security.SignInRequest
import com.epiuse.labs.epifoos.security.SignUpRequest

object CognitoService {

    fun signUp(signUpRequest: SignUpRequest): SignUpResult {
        val cognitoRequest = SignUpRequest()
            .withUsername(signUpRequest.username)
            .withClientId(APP_CLIENT_ID)
            .withUserAttributes(
                AttributeType()
                    .withName("email")
                    .withValue(signUpRequest.email),
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

        val cognitoSignUpConfirmation = ConfirmSignUpRequest()
            .withClientId(APP_CLIENT_ID)
            .withUsername(confirmSignUpRequest.email)
            .withConfirmationCode(confirmSignUpRequest.confirmationCode)

        return COGNITO_CLIENT.confirmSignUp(cognitoSignUpConfirmation)
    }

    fun signIn(signInRequest: SignInRequest): AuthenticationResultType {
        val authParams = hashMapOf(
            "USERNAME" to signInRequest.username,
            "PASSWORD" to signInRequest.password
        )

        val authRequest = AdminInitiateAuthRequest()
            .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
            .withUserPoolId(USER_POOL)
            .withClientId(APP_CLIENT_ID)
            .withAuthParameters(authParams)

        return COGNITO_CLIENT.adminInitiateAuth(authRequest).authenticationResult
    }

    private const val AWS_REGION: String = "eu-west-2"
    private const val USER_POOL: String = "eu-west-2_xJzoo0rjB"

    private var AWS_ACCESS_KEY: String = System.getenv("AWS_ACCESS_KEY")
    private var AWS_SECRET_KEY: String = System.getenv("AWS_SECRET_KEY")

    const val APP_CLIENT_ID: String = "77d9n50nd4ucv0bjkidg521nlm"
    private var APP_CLIENT_SECRET_KEY: String = System.getenv("APP_CLIENT_SECRET_KEY")

    private val COGNITO_CLIENT = AWSCognitoIdentityProviderClientBuilder.standard()
        .withCredentials(
            AWSStaticCredentialsProvider(
                BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)
            )
        )
        .withRegion(AWS_REGION)
        .build()
}