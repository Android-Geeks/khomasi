package com.company.rentafield.data.repositories.booking

import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow

interface RemoteUserBooking {
    suspend fun getUserBookings(
        token: String,
        id: String
    ): Flow<DataState<com.company.rentafield.data.models.booking.MyBookingsResponse>>

    suspend fun cancelBooking(
        token: String,
        bookingId: Int,
        isUser: Boolean
    ): Flow<DataState<com.company.rentafield.data.models.MessageResponse>>
}