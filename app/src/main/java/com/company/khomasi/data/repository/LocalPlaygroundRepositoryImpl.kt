package com.company.khomasi.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.company.khomasi.domain.repository.LocalPlaygroundRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalPlaygroundRepositoryImpl(
    private val context: Context
) : LocalPlaygroundRepository {

    override suspend fun savePlaygroundName(playgroundName: String) {
        context.dataStore.edit { settings ->
            settings[PlaygroundPreferenceKeys.PLAYGROUND_NAME] = playgroundName
        }
    }

    override fun getPlaygroundName(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PlaygroundPreferenceKeys.PLAYGROUND_NAME] ?: ""
        }
    }

    override suspend fun savePlaygroundPrice(playgroundPrice: Int) {
        context.dataStore.edit { settings ->
            settings[PlaygroundPreferenceKeys.PLAYGROUND_PRICE] = playgroundPrice
        }
    }

    override fun getPlaygroundPrice(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[PlaygroundPreferenceKeys.PLAYGROUND_PRICE] ?: 50
        }
    }
}

private object PlaygroundPreferenceKeys {
    val PLAYGROUND_NAME = stringPreferencesKey("playground_name")
    val PLAYGROUND_PRICE = intPreferencesKey("playground_price")
}