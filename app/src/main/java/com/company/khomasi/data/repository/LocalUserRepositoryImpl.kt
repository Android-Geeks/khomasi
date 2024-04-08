package com.company.khomasi.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.company.khomasi.data.repository.PreferenceKeys.CITY
import com.company.khomasi.data.repository.PreferenceKeys.COINS
import com.company.khomasi.data.repository.PreferenceKeys.COUNTRY
import com.company.khomasi.data.repository.PreferenceKeys.EMAIL
import com.company.khomasi.data.repository.PreferenceKeys.FIRST_NAME
import com.company.khomasi.data.repository.PreferenceKeys.IS_LOGIN
import com.company.khomasi.data.repository.PreferenceKeys.IS_ONBOARDING
import com.company.khomasi.data.repository.PreferenceKeys.LAST_NAME
import com.company.khomasi.data.repository.PreferenceKeys.LATITUDE
import com.company.khomasi.data.repository.PreferenceKeys.LONGITUDE
import com.company.khomasi.data.repository.PreferenceKeys.OTP_CODE
import com.company.khomasi.data.repository.PreferenceKeys.PHONE_NUMBER
import com.company.khomasi.data.repository.PreferenceKeys.PROFILE_PICTURE
import com.company.khomasi.data.repository.PreferenceKeys.RATING
import com.company.khomasi.data.repository.PreferenceKeys.SEARCH_HISTORY
import com.company.khomasi.data.repository.PreferenceKeys.TOKEN
import com.company.khomasi.data.repository.PreferenceKeys.USER_ID
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.repository.LocalUserRepository
import com.company.khomasi.navigation.Routes
import com.company.khomasi.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserRepositoryImpl(
    private val context: Context
) : LocalUserRepository {

    override fun getLocalUser(): Flow<LocalUser> {
        return context.dataStore.data.map { preferences ->
            LocalUser(
                userID = preferences[USER_ID] ?: "",
                firstName = preferences[FIRST_NAME] ?: "",
                lastName = preferences[LAST_NAME] ?: "",
                email = preferences[EMAIL] ?: "",
                phoneNumber = preferences[PHONE_NUMBER] ?: "",
                city = preferences[CITY] ?: "",
                country = preferences[COUNTRY] ?: "",
                latitude = preferences[LATITUDE] ?: 0.0,
                longitude = preferences[LONGITUDE] ?: 0.0,
                profilePicture = preferences[PROFILE_PICTURE] ?: "",
                coins = preferences[COINS] ?: 0,
                rating = preferences[RATING] ?: 0,
                token = preferences[TOKEN] ?: "",
                otpCode = preferences[OTP_CODE] ?: 0
            )
        }
    }

    override suspend fun saveLocalUser(localUser: LocalUser) {
        context.dataStore.edit { settings ->
            settings[USER_ID] = localUser.userID ?: ""
            settings[FIRST_NAME] = localUser.firstName ?: ""
            settings[LAST_NAME] = localUser.lastName ?: ""
            settings[EMAIL] = localUser.email ?: ""
            settings[PHONE_NUMBER] = localUser.phoneNumber ?: ""
            settings[CITY] = localUser.city ?: ""
            settings[COUNTRY] = localUser.country ?: ""
            settings[LATITUDE] = localUser.latitude ?: 0.0
            settings[LONGITUDE] = localUser.longitude ?: 0.0
            settings[PROFILE_PICTURE] = localUser.profilePicture ?: ""
            settings[COINS] = localUser.coins ?: 0
            settings[RATING] = localUser.rating ?: 0
            settings[TOKEN] = localUser.token ?: ""
            settings[OTP_CODE] = localUser.otpCode ?: 0
        }
    }

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[IS_ONBOARDING] = true
        }
    }

    override suspend fun saveIsLogin(isLogin: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_LOGIN] = isLogin
        }
    }

    override fun readAppEntry(): Flow<Routes> {
        return context.dataStore.data.map { preferences ->
            val appEntry = preferences[IS_ONBOARDING] ?: false
            val isLogin = preferences[IS_LOGIN] ?: false
            if (!appEntry) {
                Routes.AppStartNavigation
            } else if (!isLogin) {
                Routes.AuthNavigation
            } else {
                Routes.KhomasiNavigation
            }
        }
    }

    override fun getSearchHistory(): Flow<List<String>> {
        return context.dataStore.data.map { preferences ->
            val searchHistory = preferences[SEARCH_HISTORY] ?: setOf()
            searchHistory.toList()
        }
    }

    override suspend fun saveSearchHistory(searchQuery: String) {
        context.dataStore.edit { settings ->
            val searchHistory = settings[SEARCH_HISTORY] ?: setOf()
            val newSearchHistory = searchHistory.toMutableSet()
            newSearchHistory.add(searchQuery)
            settings[SEARCH_HISTORY] = newSearchHistory
        }
    }

    override suspend fun removeSearchHistory() {
        context.dataStore.edit { settings ->
            settings[SEARCH_HISTORY] = setOf()
        }
    }
}

private val readOnlyProperty = preferencesDataStore(name = Constants.USER_SETTINGS)

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val USER_ID = stringPreferencesKey(Constants.USER_ID)
    val FIRST_NAME = stringPreferencesKey(Constants.FIRST_NAME)
    val LAST_NAME = stringPreferencesKey(Constants.LAST_NAME)
    val EMAIL = stringPreferencesKey(Constants.EMAIL)
    val PHONE_NUMBER = stringPreferencesKey(Constants.PHONE_NUMBER)
    val CITY = stringPreferencesKey(Constants.CITY)
    val COUNTRY = stringPreferencesKey(Constants.COUNTRY)
    val LATITUDE = doublePreferencesKey(Constants.LATITUDE)
    val LONGITUDE = doublePreferencesKey(Constants.LONGITUDE)
    val PROFILE_PICTURE = stringPreferencesKey(Constants.PROFILE_PICTURE)
    val COINS = intPreferencesKey(Constants.COINS)
    val RATING = intPreferencesKey(Constants.RATING)
    val OTP_CODE = intPreferencesKey(Constants.OTP_CODE)
    val TOKEN = stringPreferencesKey(Constants.TOKEN)
    val IS_ONBOARDING = booleanPreferencesKey(Constants.IS_ONBOARDING)
    val IS_LOGIN = booleanPreferencesKey(Constants.IS_LOGIN)

    val SEARCH_HISTORY = stringSetPreferencesKey(Constants.SEARCH_HISTORY)
}