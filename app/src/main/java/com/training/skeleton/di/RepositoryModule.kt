package com.training.skeleton.di

import android.content.Context
import com.training.datastore.dao.ProductDao
import com.training.network.NetworkService
import com.training.skeleton.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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
            applicationContext = context,
        )
    }
}