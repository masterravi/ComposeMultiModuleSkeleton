package com.training.skeleton.presentation.feature_language_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.localization.LocalizationBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LanguageSettingUiState {
    val languageList: List<LanguageModel>
    val selectedLanguageCode: String
    val showLoader: Boolean

    data class ToLanguageSettingUiState(
        override val languageList: List<LanguageModel>,
        override val selectedLanguageCode: String,
        override val showLoader: Boolean
    ) : LanguageSettingUiState
}

private data class LanguageSettingViewModelState(
    val languageList: List<LanguageModel> = emptyList(),
    val selectedLanguageCode: String = DEFAULT_APP_LANGUAGE,
    val showLoader: Boolean = false
) {
    fun toLanguageSettingUiState(): LanguageSettingUiState =
        LanguageSettingUiState.ToLanguageSettingUiState(
            languageList = languageList,
            selectedLanguageCode = selectedLanguageCode,
            showLoader = showLoader
        )
}

@HiltViewModel
class LanguageSettingViewModel @Inject constructor() : ViewModel() {

    private val viewModelState =
        MutableStateFlow(
            LanguageSettingViewModelState(
                languageList = listOf(
                    LanguageModel(
                        languageName = "English",
                        languageCode = "en",
                        language_s_code = "en_US",
                        "United States"
                    )
                )
            )
        )

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toLanguageSettingUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toLanguageSettingUiState()
        )

    init {

        viewModelScope.launch(Dispatchers.IO) {

            viewModelState.update {
                it.copy(languageList = listOf(
                    LanguageModel(
                        languageName = "English",
                        languageCode = "en",
                        language_s_code = "en_US",
                        "United States"
                    ),
                    LanguageModel(
                        languageName = "Hindi",
                        languageCode = "hi",
                        language_s_code = "hi_IN",
                        "India"
                    )
                ))
            }
        }

        viewModelScope.launch {
            LocalizationBuilder.getCurrentLanguage().onEach { language->
                viewModelState.update {
                    it.copy(selectedLanguageCode = language)
                }
            }.collect()
        }
    }

    fun changeLanguage(languageModel: LanguageModel, onSuccess: (Boolean) -> Unit) {
        viewModelState.update {
            it.copy(showLoader = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = LocalizationBuilder.philology?.setAppLanguage(languageModel.languageCode, "1.0.0")

                viewModelState.update {
                    it.copy(showLoader = false)
                }
                if(response!=null && response) {
                    onSuccess.invoke(true)
                }
                else{
                    onSuccess.invoke(false)
                }
            } catch (e: Exception) {
                viewModelState.update {
                    it.copy(showLoader = false)
                }
                onSuccess.invoke(false)
            }
        }
    }
}
