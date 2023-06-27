package com.training.localization.repository

import android.content.Context
import com.google.gson.Gson
import com.training.datastore.dao.LanguageDao
import com.training.datastore.entity.LanguageEntity
import com.training.localization.LocalizationBuilder.Companion.LOCALIZATION_FILE_PREFIX
import com.training.localization.Philology
import com.training.network.NetworkService
import com.training.trainingmodule.localization.utilities.DispatcherProvider
import com.training.localization.utilities.LocalizationLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepositoryImpl @Inject constructor(
    private val languageDao: LanguageDao,
    private val dispatcherProvider: DispatcherProvider,
    private val networkService: NetworkService,
    @ApplicationContext val applicationContext: Context
) {
    private var baseUrl = "https://github.com/Gaurav2621998/ComposeMultiModuleSkeleton/blob/119865b6ca5ab92674b5646d7aed92a061017588/LanguageFiles/"


     fun getLanguageFileFromDB(languageName: String): List<LanguageEntity> {
        return languageDao.getLanguageData(languageName)
    }

     fun getLanguageFileFromDBinFlow(languageName: String): Flow<List<LanguageEntity>> {
        return languageDao.getLanguageDataFlow(languageName)
    }


    @Suppress("UNCHECKED_CAST")
     suspend fun getLanguageFileFromServer(languageName: String, versionName: String): Boolean {
        return withContext(Dispatchers.IO) {

            val fileName = getLanguageFileToReadFromLanguageCode(
                languageName,
                LOCALIZATION_FILE_PREFIX
            )

            // Uncomment it when loading file from server
//            val result =
//                networkService.getFileFromServer(baseUrl + "$fileName${LocalizationConstants.TEXT_FILE_EXTENSION}")

            // Loading file from asset
            val result = Philology.getLocalizationData(context = applicationContext, localLanguageFileName = fileName)

            if (result!= null
                // Uncomment it when loading file from server
//                && result.isSuccessful
//                && result.body() != null
//                && result.body().toString()
//                    .isNotEmpty()
            ) {

//                LocalizationLogger.debug("LanguageData direct",""+result.body().toString())

                // Uncomment it when loading file from server
               // val fileResultObject = JSONObject(result.body().toString())
                val fileResultObject = JSONObject(result);

                LocalizationLogger.debug("LanguageData",""+fileResultObject)

                val yourHashMap = Gson().fromJson(fileResultObject.toString(), Map::class.java)

                if(fileResultObject.toString() != null) {
                    languageDao.insert(
                        LanguageEntity(
                            versionName = versionName,
                            languageName = languageName,
                            languageJson = fileResultObject.toString(),
                            languageKeySet = if (!yourHashMap.isNullOrEmpty()) {
                                yourHashMap as Map<String, Any>
                            } else {
                                HashMap()
                            }
                        )
                    )
                }

                true
            } else {
                LocalizationLogger.debug("json parse issue")
                false
            }
        }
    }

     fun getLanguageFileToReadFromLanguageCode(lang: String, prefix: String): String {

        var fileName = ""
        try {
            if (lang.isNotBlank()) {
                fileName = when (lang.lowercase()) {
                    "en" -> {
                        prefix+"_en_US"
                    }
                    else -> prefix + "_" + lang.lowercase() + "_IN"
                }
            }
        } catch (e: Exception) {
            LocalizationLogger.handle(e)
        }
        return fileName

    }

}