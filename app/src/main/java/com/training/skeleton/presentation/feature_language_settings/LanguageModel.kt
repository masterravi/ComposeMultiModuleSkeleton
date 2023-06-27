package com.training.skeleton.presentation.feature_language_settings

import com.google.gson.annotations.SerializedName

data class LanguageModel(
    @SerializedName("langTitle")
    val languageName: String,
    @SerializedName("code")
    val languageCode: String,
    @SerializedName("s_code")
    val language_s_code:String,
    @SerializedName("title")
    var title:String
)