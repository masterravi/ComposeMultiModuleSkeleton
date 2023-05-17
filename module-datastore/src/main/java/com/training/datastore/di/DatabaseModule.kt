package com.training.datastore.di

import android.content.Context
import com.training.datastore.AppDatabase
import com.training.datastore.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context:Context):AppDatabase{
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase):ProductDao{
        return appDatabase.productDao()
    }
}