package com.training.datastore.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name =  "TrainingLocalization")

@Singleton
class PreferenceStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : PreferenceStorage {
    private val dataStore = context.dataStore

    /***
     * Function to save key-value pairs in Preference. Sets or updates the value in Preference
     * @param key used to identify the preference
     * @param value the value to be saved in the preference
     */
    override suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            // save the value in prefs
            preferences[key] = value
        }
    }

    /***
     * Function to return Preference value based on the Preference key
     * @param key  used to identify the preference
     * @param defaultValue value in case the Preference does not exists
     * @throws Exception if there is some error in getting the value
     * @return [Flow] of [T]
     */
    override fun <T> getFlowValue(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T> {
        return dataStore.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                // we try again to store the value in the map operator
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // return the default value if it doesn't exist in the storage
            preferences[key] ?: defaultValue
        }
    }

    /***
     * Function to return Preference value based on the Preference key
     * @param key  used to identify the preference
     * @param defaultValue value in case the Preference does not exists
     * @throws Exception if there is some error in getting the value
     * @return [T]
     */
    override suspend fun <T> getValue(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): T {
        return getFlowValue(key, defaultValue).first()
    }

    /***
     * Function to clear all the Preferences.
     */
    override suspend fun clearPreferenceStorage() {
        dataStore.edit {
            it.clear()
        }
    }
}
