package com.training.trainingmodule.localization.utilities

import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
class DispatcherProvider {
    fun getIO() = Dispatchers.IO
    fun getDefault() = Dispatchers.Default
    fun getMain() = Dispatchers.Main
}