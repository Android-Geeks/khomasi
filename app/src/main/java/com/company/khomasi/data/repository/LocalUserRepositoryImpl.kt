package com.company.khomasi.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.company.khomasi.domain.repository.LocalUserRepository
import com.company.khomasi.navigation.Routes
import com.company.khomasi.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserRepositoryImpl(
    private val context: Context
) : LocalUserRepository {

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override suspend fun saveIsLogin() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.IS_LOGIN] = true
        }
    }

    override fun readAppEntry(): Flow<Routes> {
        return context.dataStore.data.map { preferences ->
            val appEntry = preferences[PreferenceKeys.APP_ENTRY] ?: false
            val isLogin = preferences[PreferenceKeys.IS_LOGIN] ?: false
            if (!appEntry) {
                Routes.AppStartNavigation
            } else if (!isLogin) {
                Routes.AuthNavigation
            } else {
                Routes.KhomasiNavigation
            }
        }
    }
}

private val readOnlyProperty = preferencesDataStore(name = Constants.USER_SETTINGS)

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)
    val IS_LOGIN = booleanPreferencesKey(Constants.IS_LOGIN)
}