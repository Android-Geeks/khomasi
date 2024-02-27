package com.company.khomasi.domain.repository


import com.company.khomasi.navigation.Routes
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {
    suspend fun saveAppEntry()
    suspend fun saveIsLogin()
    fun readAppEntry(): Flow<Routes>
}