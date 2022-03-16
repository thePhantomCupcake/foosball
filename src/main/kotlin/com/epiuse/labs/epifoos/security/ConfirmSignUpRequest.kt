package com.epiuse.labs.epifoos.security

data class ConfirmSignUpRequest(
    val email: String,
    val confirmationCode: String
)
