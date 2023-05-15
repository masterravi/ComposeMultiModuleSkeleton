package com.training.skeleton.di

import android.content.Context
import com.training.datastore.dao.ProductDao
import com.training.network.NetworkService
import com.training.skeleton.repository.ProductRepository
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

class RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        productDao: ProductDao,
        networkService: NetworkService,
        @ApplicationContext context: Context,
    ): ProductRepository {
        return ProductRepository(
            productDao = productDao,
            networkService = networkService,
            context = context,
        )
    }
}