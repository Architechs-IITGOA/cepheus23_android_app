package com.example.cepheus23.APIs

import com.example.cepheus23.model.CreateTeamInfo
import com.example.cepheus23.model.CreateTeamResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateTeamApi {

    @POST("createteam")
    fun teamCreate(
        @Body teaminfo : CreateTeamInfo
    ): Call<CreateTeamResponse>
}