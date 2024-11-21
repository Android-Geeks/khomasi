package com.company.rentafield.data.repositories.localuser


import com.company.rentafield.presentation.navigation.components.Screens
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {
    fun getLocalUser(): Flow<com.company.rentafield.domain.models.LocalUser>
    suspend fun saveLocalUser(localUser: com.company.rentafield.domain.models.LocalUser)
    suspend fun saveAppEntry()
    suspend fun saveIsLogin(isLogin: Boolean)
    fun readAppEntry(): Flow<Screens>
    fun getSearchHistory(): Flow<List<String>>
    suspend fun saveSearchHistory(searchQuery: String)
    suspend fun removeSearchHistory()
    suspend fun savePlaygroundId(playgroundId: Int)
    fun getPlaygroundId(): Flow<Int>
}