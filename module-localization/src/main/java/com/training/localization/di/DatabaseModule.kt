package com.training.localization.di

import android.content.Context
import com.training.datastore.dao.LanguageDao
import com.training.localization.repository.LanguageRepositoryImpl
import com.training.network.NetworkService
import com.training.trainingmodule.localization.utilities.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideLanguageRepository(
        languageDao: LanguageDao,
        dispatcherProvider: DispatcherProvider,
        networkService: NetworkService,
        @ApplicationContext appContext: Context
    ): LanguageRepositoryImpl {
        return LanguageRepositoryImpl(
            languageDao = languageDao,
            dispatcherProvider = dispatcherProvider,
            networkService = networkService,
            applicationContext = appContext
        )
    }

}
