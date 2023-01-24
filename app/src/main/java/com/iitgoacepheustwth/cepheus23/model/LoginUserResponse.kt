package com.iitgoacepheustwth.cepheus23.model

data class LoginUserResponse (
    val token: String,
    val user: LoggedInUser
)