package com.example.cepheus23.model

data class LoggedInUser(
//    val user_uuid: String,
//    val email: String,
//    val user_name: String,
    val college: String?,
    val email: String,
    val grade: Int?,
    val id: Int?,
    val mobile: String?,
    val registered: Boolean,
    val user_name: String,
    val user_uuid: String,
    val image_url : String
)