package com.training.datastore.preferences

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferenceStorage {
    fun <T> getFlowValue(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T>

    suspend fun <T> getValue(
        key: Preferences.Key<T>,
        defaultValue: T
    ): T

    suspend fun <T> setValue(key: Preferences.Key<T>, value: T)

    suspend fun clearPreferenceStorage()
}
