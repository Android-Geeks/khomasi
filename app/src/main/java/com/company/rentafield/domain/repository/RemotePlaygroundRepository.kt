package com.company.rentafield.domain.repository

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.booking.BookingPlaygroundResponse
import com.company.rentafield.domain.model.playground.BookingRequest
import com.company.rentafield.domain.model.playground.FreeTimeSlotsResponse
import com.company.rentafield.domain.model.playground.PlaygroundReviewsResponse
import com.company.rentafield.domain.model.search.FilteredPlaygroundResponse
import kotlinx.coroutines.flow.Flow

interface RemotePlaygroundRepository {
    suspend fun getFreeSlots(
        token: String,
        id: Int,
        dayDiff: Int
    ): Flow<DataState<FreeTimeSlotsResponse>>

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