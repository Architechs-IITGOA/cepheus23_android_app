package com.example.cepheus23.model

data class LoginUserResponse (
    val token: String,
    val user: LoggedInUser
)