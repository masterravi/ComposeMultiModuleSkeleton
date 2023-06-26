package com.training.localization.repository

import com.google.gson.Gson
import com.training.datastore.dao.LanguageDao
import com.training.localization.LocalizationBuilder.Companion.LOCALIZATION_FILE_PREFIX
import com.training.network.NetworkService
import com.training.datastore.entity.LanguageEntity
import com.training.trainingmodule.localization.utilities.DispatcherProvider
import com.training.trainingmodule.localization.utilities.LangConstants
import com.training.trainingmodule.localization.utilities.LocalizationLogger
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
    private val networkService: NetworkService
) {
    private var baseUrl = ""


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


            val result =
                networkService.getFileFromServer(baseUrl + "$fileName${LangConstants.TEXT_FILE_EXTENSION}")

            if (result!= null && result.isSuccessful && result.body() != null && result.body().toString()
                    .isNotEmpty()
            ) {

                val fileResultObject = JSONObject(result.body().toString())

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