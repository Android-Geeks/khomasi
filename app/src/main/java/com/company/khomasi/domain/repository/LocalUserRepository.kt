package com.company.khomasi.domain.repository


import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.navigation.Routes
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {
    fun getLocalUser(): Flow<LocalUser>
    suspend fun saveLocalUser(localUser: LocalUser)
    suspend fun saveAppEntry()
    suspend fun saveIsLogin()
    fun readAppEntry(): Flow<Routes>
    fun getSearchHistory(): Flow<List<String>>
    suspend fun saveSearchHistory(searchQuery: String)
    suspend fun removeSearchHistory()
}