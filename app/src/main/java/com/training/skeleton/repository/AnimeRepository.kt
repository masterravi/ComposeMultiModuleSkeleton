package com.training.skeleton.repository

import android.content.Context
import android.util.Log
import com.training.skeleton.feature_dashboard.data.Anime
import com.training.skeleton.network.DataState
import com.training.skeleton.network.NetworkClient
import com.training.trainingmodule.network.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AnimeRepository(val context: Context) {

    fun fetchAnimeList(): Flow<DataState<List<Anime>>> = flow {
        val networkService= NetworkClient().getInstance(context)
            .create(NetworkService::class.java)
        try {
            val response = networkService.getAnimeList()
            if (response.isSuccessful && response.body() != null) {
                    emit(DataState.Success(response.body()!!))
                } else {
                    // response from serve on failure
                    emit(DataState.Error("Something went wrong. Please try again later"))
                }
        } catch (e: Exception) {
            Log.e("Exception",e.message.toString())
        }
    }
}