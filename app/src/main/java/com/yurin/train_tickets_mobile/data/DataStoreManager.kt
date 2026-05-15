package com.yurin.train_tickets_mobile.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.userSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")
private val accessToken = stringPreferencesKey("accessToken")
private val baseURL = stringPreferencesKey("baseUrl")

object DataStoreManager {

    suspend fun saveValue(context: Context, value: String) {
        context.userSettingsDataStore.edit {
            it[accessToken] = value
        }
    }

    suspend fun getStringValue(context: Context): String? {
        val valueFlow: Flow<String?> = context.userSettingsDataStore.data.map {
            it[accessToken]
        }
        return valueFlow.first()
    }

    suspend fun setBaseUrl(context: Context, value: String) {
        context.userSettingsDataStore.edit {
            it[baseURL] = value
        }
    }

    suspend fun getBaseUrl(context: Context): String? {
        val valueFlow: Flow<String?> = context.userSettingsDataStore.data.map {
            it[baseURL]
        }
        return valueFlow.first()
    }
}