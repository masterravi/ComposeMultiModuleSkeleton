package com.training.trainingmodule.localization.di

import android.content.Context
import com.training.localization.room.AppDatabase
import com.training.localization.room.dao.LanguageDao
import com.training.trainingmodule.localization.repository.LanguageRepositoryImpl
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

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Provides
    fun provideLanguageDao(appDatabase: AppDatabase): LanguageDao {
        return appDatabase.LanguageDao()
    }

    @Singleton
    @Provides
    fun provideLanguageRepository(
        languageDao: LanguageDao,
        dispatcherProvider: DispatcherProvider
    ): LanguageRepositoryImpl {
        return LanguageRepositoryImpl(
            languageDao = languageDao,
            dispatcherProvider = dispatcherProvider
        )
    }

}
