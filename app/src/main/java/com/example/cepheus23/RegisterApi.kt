package com.example.cepheus23

import com.example.cepheus23.model.UserInfo
import com.example.cepheus23.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {

    @POST("register")
    fun registerUser(
        @Body userinfo : UserInfo
    ): Call<UserResponse>
}

