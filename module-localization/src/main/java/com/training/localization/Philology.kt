package com.training.localization

import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.training.trainingmodule.localization.data.room.entity.LanguageEntity
import com.training.trainingmodule.localization.repository.LanguageRepositoryImpl
import com.training.trainingmodule.localization.utilities.LocalizationConstants
import com.training.trainingmodule.localization.utilities.LocalizationLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Suppress("UNCHECKED_CAST")
@Singleton
class Philology @Inject constructor(
    private val baseUrl: String,
    val repository: LanguageRepositoryImpl,
    private val localLanguageFileName: String,
    @ApplicationContext val applicationContext: Context,
) {

    companion object {

        fun wrap(baseContext: Context): ContextWrapper = PhilologyContextWrapper(baseContext)

        private val currentLanguage: MutableLiveData<String> =
            MutableLiveData(LocalizationConstants.DEFAULT_APP_LANGUAGE)
        var languageEntity: MutableLiveData<LanguageEntity?> = MutableLiveData(null)

        private var defaultLanguageData: MutableLiveData<Map<String, Any>?> = MutableLiveData(null)
        private var defaultData: MutableLiveData<Map<String, Any>?> = MutableLiveData(null)

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

        fun String.getString(id: String): String {
            return if (currentLanguage.value.equals(LocalizationConstants.DEFAULT_APP_LANGUAGE)) {
                when {
                    defaultLanguageData.value?.containsKey(id) == true && defaultLanguageData.value?.get(
                        id
                    ).toString().isNotEmpty() -> {
                        defaultLanguageData.value?.get(id).toString()
                    }
                    defaultData.value?.containsKey(id) == true -> {
                        defaultData.value?.get(id).toString()
                    }
                    else -> this
                }
            } else {
                if (languageEntity.value?.languageKeySet?.containsKey(id) == true && languageEntity.value?.languageKeySet!![id].toString()
                        .isNotEmpty()
                ) {
                    languageEntity.value?.languageKeySet!![id].toString()
                } else {
                    when {
                        defaultLanguageData.value?.containsKey(id) == true && defaultLanguageData.value?.get(
                            id
                        ).toString().isNotEmpty() -> {
                            defaultLanguageData.value?.get(id).toString()
                        }
                        defaultData.value?.containsKey(id) == true -> {
                            defaultData.value?.get(id).toString()
                        }
                        else -> this
                    }
                }
            }
        }

        fun getString(id: String): String {
            // TrainingAppLogger.debug("StringFile : ","$id" + defaultLanguageData.value + languageEntity.value)
            return if (currentLanguage.value.equals(LocalizationConstants.DEFAULT_APP_LANGUAGE)) {
                when {
                    defaultLanguageData.value?.containsKey(id) == true && defaultLanguageData.value?.get(
                        id
                    ).toString().isNotEmpty() -> {
                        defaultLanguageData.value?.get(id).toString()
                    }
                    defaultData.value?.containsKey(id) == true -> {
                        defaultData.value?.get(id).toString()
                    }
                    else -> {
                        ""
                    }
                }
            } else {
                if (languageEntity.value?.languageKeySet?.containsKey(id) == true && languageEntity.value?.languageKeySet!![id].toString()
                        .isNotEmpty()
                ) {
                    languageEntity.value?.languageKeySet!![id].toString()
                } else {
                    when {
                        defaultLanguageData.value?.containsKey(id) == true && defaultLanguageData.value?.get(
                            id
                        ).toString().isNotEmpty() -> {
                            defaultLanguageData.value?.get(id).toString()
                        }
                        defaultData.value?.containsKey(id) == true -> {
                            defaultData.value?.get(id).toString()
                        }
                        else -> {
                            ""
                        }
                    }
                }
            }
        }

        // TODO - Currently pointing to asset json , later pointing to db json file
        fun getConfigData(context: Context) = MutableLiveData(
            Gson().fromJson(
                Utils.getJsonFromAssets(
                    context,
                    "TrainingAppDefaultLocalisationFileName",
                    fileType = "txt"
                ), JsonObject::class.java
            )
        )

        fun getLocalizationData(context: Context, localLanguageFileName: String): String? {
            val jsonString = Utils.getJsonFromAssets(
                context,
                localLanguageFileName.split(".")[0],
                fileType = if (localLanguageFileName.split(".").size > 1) localLanguageFileName.split(
                    "."
                )[1] else "txt"
            )
            return if (jsonString != null && Utils.isValidJsonString(jsonString)) {
                jsonString
            } else {
                null
            }
        }
    }


    val context: CoroutineContext = Job() + Dispatchers.IO
    val coroutineScope: CoroutineScope = CoroutineScope(context)

    init {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val langJsonString = getLocalizationData(applicationContext, localLanguageFileName)
                langJsonString?.let {
                    val langJsonObject = Gson().fromJson(it, JsonObject::class.java)
                    val yourHashMap =
                        Gson().fromJson(langJsonObject, Map::class.java)
                    defaultLanguageData.postValue(yourHashMap as Map<String, Any>)
                    defaultData.postValue(yourHashMap)
                    LocalizationLogger.debug("LanguageData from asset" + yourHashMap)
                }
            } catch (e: Exception) {
                LocalizationLogger.debug("exception language from asset" + e.localizedMessage)
                LocalizationLogger.handle(e)
            }
            repository.getLanguageFileFromDBinFlow(LocalizationConstants.DEFAULT_APP_LANGUAGE)
                .collect {
                    try {
                        if (it.isNotEmpty()) {
                            defaultLanguageData.postValue(
                                it[0].languageKeySet
                            )
                            LocalizationLogger.debug("LanguageData from db " + it[0].languageKeySet)
                        }
                    } catch (e: Exception) {
                        LocalizationLogger.debug("exception language data from db" + e.localizedMessage)
                        LocalizationLogger.handle(e)
                    }
                }
        }
    }

    suspend fun setAppLanguage(language: String, fileVersion:String):Boolean{

        var result = true

        val languageData = repository.getLanguageFileFromDB(language)

        when {
            languageData.isEmpty() && language != LocalizationConstants.DEFAULT_APP_LANGUAGE -> {
                val response =
                    repository.getLanguageFileFromServer(
                        language,
                        fileVersion ?: "1.0.0"
                    )
                result = response
                if (response) {
                    languageEntity.postValue(
                        repository.getLanguageFileFromDB(
                            language
                        )[0]
                    )
                }
            }
            languageData.isNotEmpty() && language != LocalizationConstants.DEFAULT_APP_LANGUAGE && fileVersion != languageData[0].versionName -> {
                val response =
                    repository.getLanguageFileFromServer(
                        language,
                        fileVersion ?: "1.0.0"
                    )
                if (response) {
                    languageEntity.postValue(
                        repository.getLanguageFileFromDB(
                            language
                        )[0]
                    )
                }
            }
            language != LocalizationConstants.DEFAULT_APP_LANGUAGE -> {
                languageEntity.postValue(languageData.get(0))
            }
            language == LocalizationConstants.DEFAULT_APP_LANGUAGE -> {
                if (languageData.isNotEmpty() && fileVersion != languageData[0].versionName) {
                    val response = repository.getLanguageFileFromServer(
                        language,
                        fileVersion ?: "1.0.0"
                    )
                } else if (languageData.isEmpty()) {
                    repository.getLanguageFileFromServer(
                        language,
                        fileVersion ?: "1.0.0"
                    )
                }
            }
        }
        return result
    }

    suspend fun checkCurrentLanguageFileVersion(language: String, fileVersion: String){

        val languageData = repository.getLanguageFileFromDB(language)

        if(languageData.isNotEmpty() && languageData[0].versionName!=fileVersion){
            val response =
                repository.getLanguageFileFromServer(
                    language,
                    fileVersion
                )
            if(response && language!=LocalizationConstants.DEFAULT_APP_LANGUAGE){
                languageEntity.postValue(
                    repository.getLanguageFileFromDB(
                        language
                    )[0]
                )
            }
        }

    }

}


