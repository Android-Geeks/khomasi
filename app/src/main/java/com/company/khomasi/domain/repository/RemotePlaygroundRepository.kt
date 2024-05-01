package com.company.khomasi.domain.repository

import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import com.company.khomasi.domain.model.FilteredPlaygroundResponse
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime

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

    suspend fun getFilteredPlaygrounds(
        token: String,
        id: String,
        price: Int,
        bookingTime: LocalDateTime,
        duration: Double
    ): Flow<DataState<FilteredPlaygroundResponse>>
}