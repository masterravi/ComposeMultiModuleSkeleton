package com.training.skeleton.repository

import android.content.Context
import android.util.Log
import com.training.network.DataState
import com.training.network.NetworkClient
import com.training.network.NetworkService
import com.training.network.response.ProductDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository(val context: Context) {

    fun fetchProductList(): Flow<DataState<List<ProductDetail>>> = flow {
        val networkService= NetworkClient().getInstance(context)
            .create(NetworkService::class.java)
        try {
            val response = networkService.getProductList()
            if (response.isSuccessful && response.body() != null) {
                    emit(DataState.Success(response.body()!!.products))
                } else {
                    // response from serve on failure
                    emit(DataState.Error("Something went wrong. Please try again later"))
                }
        } catch (e: Exception) {
            Log.e("Exception",e.message.toString())
        }
    }
}