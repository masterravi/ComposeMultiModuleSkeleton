package com.training.localization

import android.content.Context
import com.training.datastore.AppDatabase
import com.training.localization.repository.LanguageRepositoryImpl
import com.training.network.NetworkClient
import com.training.network.NetworkService
import com.training.trainingmodule.localization.utilities.DispatcherProvider
import com.training.trainingmodule.localization.utilities.LangConstants
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class LocalizationBuilder(
    val baseUrl: String,
    val localLanguageFileName: String,
    val languageFileNamePrefix: String,
    val context: Context
) {
    companion object {
        var LOCALIZATION_FILE_PREFIX = ""
        var languageRepositoryImpl: LanguageRepositoryImpl? = null
        var philology: Philology? = null

        fun getCurrentLanguage() = flow {
            emit(LangConstants.DEFAULT_APP_LANGUAGE)
        }
    }

    init {

        val languageDao = AppDatabase.getInstance(context).LanguageDao()
        val networkService= NetworkClient(context).getInstance().create(NetworkService::class.java)

        LOCALIZATION_FILE_PREFIX = languageFileNamePrefix
        languageRepositoryImpl = LanguageRepositoryImpl(
            languageDao = languageDao,
            dispatcherProvider = DispatcherProvider(),
            networkService = networkService
        )

        philology = Philology(
            baseUrl = baseUrl,
            applicationContext = context,
            repository = languageRepositoryImpl!!,
            localLanguageFileName = localLanguageFileName,
        )

    }
}