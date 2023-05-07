package com.training.network

import com.training.network.response.Product
import retrofit2.Response
import retrofit2.http.*


interface NetworkService {

    @GET("/products")
    suspend fun getProductList(): Response<Product>
}











