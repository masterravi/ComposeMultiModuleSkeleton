package com.training.trainingmodule.localization.network

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface NetworkInterface {

    @Streaming
    @GET
    suspend fun getFileFromServer(@Url url: String): retrofit2.Response<JsonObject>
}
