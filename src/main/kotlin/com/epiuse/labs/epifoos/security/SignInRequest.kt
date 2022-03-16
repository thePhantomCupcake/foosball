package com.epiuse.labs.epifoos.security

data class SignInRequest(
    val email: String,
    val password: String
)
