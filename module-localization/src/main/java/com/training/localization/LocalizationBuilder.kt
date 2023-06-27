package com.training.localization

import android.content.Context
import com.training.datastore.AppDatabase
import com.training.datastore.preferences.PreferenceConstants
import com.training.datastore.preferences.PreferenceStorageImpl
import com.training.localization.repository.LanguageRepositoryImpl
import com.training.trainingmodule.localization.utilities.DispatcherProvider
import com.training.localization.utilities.LocalizationConstants
import com.training.network.NetworkClient
import com.training.network.NetworkService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import javax.inject.Singleton

@Singleton
class LocalizationBuilder(
    val localLanguageFileName:String,
    val languageFileNamePrefix:String,
    val context: Context
) {
    companion object {
        var LOCALIZATION_FILE_PREFIX = "AndroidLocalization"
        var languageRepositoryImpl: LanguageRepositoryImpl? = null
        var preferenceStorage: PreferenceStorageImpl? = null
        var philology:Philology?=null


        fun getCurrentLanguage() = flow {
            preferenceStorage?.getFlowValue(
                PreferenceConstants.KEY_PREF_APP_LANGUAGE,
                LocalizationConstants.DEFAULT_APP_LANGUAGE
            )
                ?.onEach {
                    emit(it)
                }?.collect()
        }
    }

    init {
        val languageDao = AppDatabase.getInstance(context).LanguageDao()
        preferenceStorage = PreferenceStorageImpl(context)
        val networkService= NetworkClient(context).getInstance().create(NetworkService::class.java)

        LOCALIZATION_FILE_PREFIX = languageFileNamePrefix
        languageRepositoryImpl = LanguageRepositoryImpl(
            languageDao = languageDao,
            dispatcherProvider = DispatcherProvider(),
            networkService = networkService,
            applicationContext = context
        )

        philology = Philology(
            baseUrl = "",
            applicationContext = context,
            repository = languageRepositoryImpl!!,
            preferenceStorage = preferenceStorage!!,
            localLanguageFileName = localLanguageFileName
        )
    }
}