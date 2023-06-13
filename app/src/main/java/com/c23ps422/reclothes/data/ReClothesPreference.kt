package com.c23ps422.reclothes.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReClothesPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getToken(): Flow<String?> {
        return dataStore.data.map { pref ->
            pref[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { pref ->
            pref[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        dataStore.edit { pref ->
            pref.remove(TOKEN_KEY)
        }
    }

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")

        @Volatile
        private var INSTANCE: ReClothesPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): ReClothesPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = ReClothesPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}