package com.example.cepheus23.APIs


import com.example.cepheus23.model.LoginUserInfo
import com.example.cepheus23.model.LoginUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    fun loginUser(
        @Body loginUserinfo : LoginUserInfo
    ): Call<LoginUserResponse>
}