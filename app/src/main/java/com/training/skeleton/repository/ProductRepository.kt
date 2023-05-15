package com.training.skeleton.repository

import android.content.Context
import android.util.Log
import com.training.datastore.AppDatabase
import com.training.datastore.dao.ProductDao
import com.training.datastore.entity.ProductEntity
import com.training.network.DataState
import com.training.network.NetworkClient
import com.training.network.NetworkService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class ProductRepository(
    @ApplicationContext private val applicationContext: Context,
    private val  productDao: ProductDao,
    private val  networkService: NetworkService
) {

    fun getProductList()= AppDatabase.getInstance(applicationContext).productDao().getAllProduct()
    fun fetchProductList(): Flow<DataState<String>> = flow {
        val networkService= NetworkClient().getInstance(applicationContext)
            .create(NetworkService::class.java)
        try {
            val response = networkService.getProductList()
            if (response.isSuccessful && response.body() != null) {
                    val database = AppDatabase.getInstance(applicationContext)
                    if(!response.body()!!.products.isEmpty()){
                        val products= response.body()!!.products
                        products.forEach {
                            val productEntity= ProductEntity(
                                id=it.id,
                                brand = it.brand?:"",
                                price = it.price?:0,
                                category = it.category?:"",
                                description = it.description?:"",
                                rating = it.rating?:0.00,
                                thumbnail = it.thumbnail?:"",
                                title = it.title?:""
                            )
                            database.productDao().insert(productEntity)
                        }
                    }
                    emit(DataState.Success("0"))
                } else {
                    // response from serve on failure
                    emit(DataState.Error("Something went wrong. Please try again later"))
                }
        } catch (e: Exception) {
            Log.e("Exception",e.message.toString())
        }
    }
}