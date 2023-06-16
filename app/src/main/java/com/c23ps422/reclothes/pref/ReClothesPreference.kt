package com.c23ps422.reclothes.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * ReClothesPreference is a Singleton class that manages application preferences using Jetpack DataStore.
 * It encapsulates operations such as getting, saving, and clearing tokens from the DataStore.
 *
 * It requires an instance of DataStore<Preferences> as an argument in its private constructor.
 *
 * Functions:
 * 1. getToken(): Retrieves the token from the DataStore. It returns a Flow<String?> which can be collected to get the current value.
 * 2. saveToken(token: String): A suspend function that saves the given token to the DataStore.
 * 3. clearToken(): Another suspend function which removes the token from the DataStore.
 *
 * Note: The saveToken() and clearToken() functions are suspend functions which need to be called from a coroutine or another suspend function.
 */
class ReClothesPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getToken(): Flow<String?> {
        return dataStore.data.map { pref ->
            pref[TOKEN_KEY]
        }
    }

    fun getId(): Flow<String?> {
        return dataStore.data.map { pref ->
            pref[ID_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { pref ->
            pref[TOKEN_KEY] = token
        }
    }

    suspend fun saveId(id: String) {
        dataStore.edit { pref ->
            pref[ID_KEY] = id
        }
    }

    suspend fun clearToken() {
        dataStore.edit { pref ->
            pref.remove(TOKEN_KEY)
        }
    }

    suspend fun clearId() {
        dataStore.edit { pref ->
            pref.remove(ID_KEY)
        }
    }

    /**
     * The companion object declares a stringPreferencesKey for the token and a Singleton instance of ReClothesPreference.
     * It has a method to get the Singleton instance, initializing it if it doesn't already exist.
     */
    companion object {
        val ID_KEY = stringPreferencesKey("id")
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