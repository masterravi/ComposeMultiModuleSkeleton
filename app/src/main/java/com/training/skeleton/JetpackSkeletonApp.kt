package com.training.skeleton

import android.app.Application
import com.training.localization.LocalizationBuilder
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class JetpackSkeletonApp: Application() {

    init {
        CoroutineScope(Dispatchers.Default).launch {
            LocalizationBuilder(
                localLanguageFileName = "AndroidLocalization_en_US",
                languageFileNamePrefix = "AndroidLocalization",
                context= this@JetpackSkeletonApp
            )
        }
    }
}