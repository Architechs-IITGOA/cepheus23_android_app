package com.example.cepheus23.APIs

import com.example.cepheus23.model.GetRegInfo
import com.example.cepheus23.model.RegisteredEventList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GetEventsApi {

    @POST("getreg")
    fun getEvents(
        @Body getreginfo : GetRegInfo
    ): Call<RegisteredEventList>
}