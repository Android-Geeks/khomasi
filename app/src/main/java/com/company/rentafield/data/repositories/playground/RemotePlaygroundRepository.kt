package com.company.rentafield.data.repositories.playground

import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow

interface RemotePlaygroundRepository {
    suspend fun getPlaygrounds(
        token: String,
        userId: String
    ): Flow<DataState<com.company.rentafield.domain.models.playground.PlaygroundsResponse>>

    suspend fun getFreeSlots(
        token: String,
        id: Int,
        dayDiff: Int
    ): Flow<DataState<com.company.rentafield.domain.models.playground.FreeTimeSlotsResponse>>

    suspend fun getPlaygroundReviews(
        token: String,
        id: Int
    ): Flow<DataState<com.company.rentafield.domain.models.playground.PlaygroundReviewsResponse>>

    suspend fun getFilteredPlaygrounds(
        token: String,
        id: String,
        price: Int,
        type: Int,
        bookingTime: String,
        duration: Double
    ): Flow<DataState<com.company.rentafield.domain.models.search.FilteredPlaygroundResponse>>

    suspend fun bookingPlayground(
        token: String,
        body: com.company.rentafield.domain.models.playground.BookingRequest
    ): Flow<DataState<com.company.rentafield.domain.models.booking.BookingPlaygroundResponse>>
}