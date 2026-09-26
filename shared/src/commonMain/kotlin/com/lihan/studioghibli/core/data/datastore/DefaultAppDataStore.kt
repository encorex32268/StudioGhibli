package com.lihan.studioghibli.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import com.lihan.studioghibli.core.domain.datastore.AppDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.IOException

class DefaultAppDataStore(
    private val dataStore: DataStore<Preferences>
) : AppDataStore {

    private companion object {
        val KEY_DATA_EXPIRED = longPreferencesKey("data_expired")
    }

    override val dataExpired: Flow<Long> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_DATA_EXPIRED] ?: 0L
        }

    override suspend fun getDataExpired(): Long {
        return dataExpired.first()
    }

    override suspend fun setDataExpired(timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_DATA_EXPIRED] = timestamp
        }
    }
}
