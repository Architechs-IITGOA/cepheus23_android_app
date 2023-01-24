package com.iitgoacepheustwth.cepheus23.APIs

import com.iitgoacepheustwth.cepheus23.model.GetRegInfo
import com.iitgoacepheustwth.cepheus23.model.RegisteredEventList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GetEventsApi {

    @POST("getreg")
    fun getEvents(
        @Body getreginfo : GetRegInfo
    ): Call<RegisteredEventList>
}