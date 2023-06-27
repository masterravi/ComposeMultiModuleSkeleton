package com.training.localization.di

import android.content.Context
import com.training.localization.Philology
import com.training.datastore.preferences.PreferenceStorageImpl
import com.training.localization.repository.LanguageRepositoryImpl
import com.training.trainingmodule.localization.utilities.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilityModule {

    @Singleton
    @Provides
    fun providePhilology(
        repository: LanguageRepositoryImpl,
        preferenceStorage: PreferenceStorageImpl,
        @ApplicationContext context: Context,
    ) : Philology {
        return Philology(
            baseUrl = "",
            repository = repository,
            localLanguageFileName = "AndroidLocalization_en_US",
            preferenceStorage = preferenceStorage,
            applicationContext = context
        )
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatcherUtility(
    ): DispatcherProvider {
        return DispatcherProvider()
    }
}