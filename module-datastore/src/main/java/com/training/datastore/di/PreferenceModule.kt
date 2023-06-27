package com.training.datastore.di


import com.training.datastore.preferences.PreferenceStorage
import com.training.datastore.preferences.PreferenceStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {
    @Binds
    abstract fun providesPreferenceStorage(
        appPreferenceStorage: PreferenceStorageImpl
    ): PreferenceStorage
}
