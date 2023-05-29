package com.training.localization

import android.content.Context
import com.training.trainingmodule.localization.data.room.AppDatabase
import com.training.trainingmodule.localization.preferences.PreferenceStorageImpl
import com.training.trainingmodule.localization.repository.LanguageRepositoryImpl
import com.training.trainingmodule.localization.utilities.DispatcherProvider
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
        var preferenceStorage: PreferenceStorageImpl? = null
        var philology: Philology? = null

        fun getCurrentLanguage() = flow {
            preferenceStorage?.getFlowValue(
                LocalizationPreferenceConstants.KEY_PREF_APP_LANGUAGE,
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

        LOCALIZATION_FILE_PREFIX = languageFileNamePrefix
        languageRepositoryImpl = LanguageRepositoryImpl(
            languageDao = languageDao,
            preferenceStorage = preferenceStorage!!,
            dispatcherProvider = DispatcherProvider()
        )

        philology = Philology(
            baseUrl = baseUrl,
            applicationContext = context,
            repository = languageRepositoryImpl!!,
            preferenceStorage = preferenceStorage!!,
            localLanguageFileName = localLanguageFileName,
        )

    }
}