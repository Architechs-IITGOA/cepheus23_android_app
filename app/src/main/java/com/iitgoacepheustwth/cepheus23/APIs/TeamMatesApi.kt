package com.iitgoacepheustwth.cepheus23.APIs

import com.iitgoacepheustwth.cepheus23.model.LoginUserInfo
import com.iitgoacepheustwth.cepheus23.model.LoginUserResponse
import com.iitgoacepheustwth.cepheus23.model.TeamMates
import com.iitgoacepheustwth.cepheus23.model.TokenNEventID
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TeamMatesApi {
    @POST("getteam")
    fun getteam(
        @Body eventTokenParam : TokenNEventID
    ): Call<TeamMates>
}