package com.company.rentafield.domain.repository


import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.navigation.Screens
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {
    fun getLocalUser(): Flow<LocalUser>
    suspend fun saveLocalUser(localUser: LocalUser)
    suspend fun saveAppEntry()
    suspend fun saveIsLogin(isLogin: Boolean)
    fun readAppEntry(): Flow<Screens>
    fun getSearchHistory(): Flow<List<String>>
    suspend fun saveSearchHistory(searchQuery: String)
    suspend fun removeSearchHistory()
    suspend fun savePlaygroundId(playgroundId: Int)
    fun getPlaygroundId(): Flow<Int>
}