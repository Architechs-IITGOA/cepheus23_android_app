package com.iitgoacepheustwth.cepheus23.APIs

import com.iitgoacepheustwth.cepheus23.model.RegisterEventInfo
import com.iitgoacepheustwth.cepheus23.model.RegisterEventResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterEventApi {

    @POST("regevent")
    fun eventregistration(
        @Body eventinfo: RegisterEventInfo
    ):Call<RegisterEventResponse>
}