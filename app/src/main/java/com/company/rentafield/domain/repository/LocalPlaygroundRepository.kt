package com.company.rentafield.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalPlaygroundRepository {
    suspend fun savePlaygroundName(playgroundName: String)
    fun getPlaygroundName(): Flow<String>
    suspend fun savePlaygroundPrice(playgroundPrice: Int)
    fun getPlaygroundPrice(): Flow<Int>
}