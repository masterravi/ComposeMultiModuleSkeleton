package com.training.trainingmodule.network

import com.training.skeleton.feature_dashboard.data.Anime
import retrofit2.Response
import retrofit2.http.*


@Suppress("unused")
interface NetworkService {

    @GET("/asarode/anime-list/master/data/data.json")
    suspend fun getAnimeList(): Response<List<Anime>>
}











