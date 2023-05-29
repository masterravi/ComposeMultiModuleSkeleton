package com.training.localization

import android.content.Context
import com.training.localization.room.AppDatabase
import com.training.trainingmodule.localization.repository.LanguageRepositoryImpl
import com.training.trainingmodule.localization.utilities.DispatcherProvider
import com.training.trainingmodule.localization.utilities.LocalizationConstants
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
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
            emit(LocalizationConstants.DEFAULT_APP_LANGUAGE)
        }
    }

    init {

        val languageDao = AppDatabase.getInstance(context).LanguageDao()

        LOCALIZATION_FILE_PREFIX = languageFileNamePrefix
        languageRepositoryImpl = LanguageRepositoryImpl(
            languageDao = languageDao,
            dispatcherProvider = DispatcherProvider()
        )

        philology = Philology(
            baseUrl = baseUrl,
            applicationContext = context,
            repository = languageRepositoryImpl!!,
            localLanguageFileName = localLanguageFileName,
        )

    }
}