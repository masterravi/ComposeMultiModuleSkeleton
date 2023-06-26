package com.training.trainingmodule.localization.di

import com.training.datastore.dao.LanguageDao
import com.training.localization.repository.LanguageRepositoryImpl
import com.training.network.NetworkService
import com.training.trainingmodule.localization.utilities.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        networkService: NetworkService
    ): LanguageRepositoryImpl {
        return LanguageRepositoryImpl(
            languageDao = languageDao,
            dispatcherProvider = dispatcherProvider,
            networkService = networkService
        )
    }

}
