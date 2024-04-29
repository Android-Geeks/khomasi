package com.company.khomasi.domain.repository

import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import kotlinx.coroutines.flow.Flow

interface RemotePlaygroundRepository {
    suspend fun getFreeSlots(
        token: String,
        id: Int,
        dayDiff: Int
    ): Flow<DataState<FessTimeSlotsResponse>>

    suspend fun getPlaygroundReviews(
        token: String,
        id: Int
    ): Flow<DataState<PlaygroundReviewResponse>>
}