package com.epiuse.labs.epifoos.security

data class SignUpRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)
