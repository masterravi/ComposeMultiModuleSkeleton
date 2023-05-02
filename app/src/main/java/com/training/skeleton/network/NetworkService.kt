package com.training.trainingmodule.network

import com.training.skeleton.feature_dashboard.data.Anime
import com.training.skeleton.feature_dashboard.data.Product
import retrofit2.Response
import retrofit2.http.*


@Suppress("unused")
interface NetworkService {

    @GET("/products")
    suspend fun getProductList(): Response<Product>
}











