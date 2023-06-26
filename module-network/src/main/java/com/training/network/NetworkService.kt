package com.training.network

import com.google.gson.JsonObject
import com.training.network.response.Product
import retrofit2.Response
import retrofit2.http.*


interface NetworkService {

    @GET("/products")
    suspend fun getProductList(): Response<Product>

    @Streaming
    @GET
    suspend fun getFileFromServer(@Url url: String): Response<JsonObject>
}











