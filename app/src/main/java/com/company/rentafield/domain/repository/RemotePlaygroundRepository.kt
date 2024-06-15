package com.company.rentafield.domain.repository

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.BookingPlaygroundResponse
import com.company.rentafield.domain.model.BookingRequest
import com.company.rentafield.domain.model.FessTimeSlotsResponse
import com.company.rentafield.domain.model.FilteredPlaygroundResponse
import com.company.rentafield.domain.model.PlaygroundReviewsResponse
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
    ): Flow<DataState<PlaygroundReviewsResponse>>

    suspend fun getFilteredPlaygrounds(
        token: String,
        id: String,
        price: Int,
        type: Int,
        bookingTime: String,
        duration: Double
    ): Flow<DataState<FilteredPlaygroundResponse>>

    suspend fun bookingPlayground(
        token: String,
        body: BookingRequest
    ): Flow<DataState<BookingPlaygroundResponse>>
}