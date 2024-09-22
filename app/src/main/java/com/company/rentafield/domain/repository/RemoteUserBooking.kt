package com.company.rentafield.domain.repository

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.booking.MyBookingsResponse
import kotlinx.coroutines.flow.Flow

interface RemoteUserBooking {
    suspend fun getUserBookings(token: String, id: String): Flow<DataState<MyBookingsResponse>>
    suspend fun cancelBooking(
        token: String,
        bookingId: Int,
        isUser: Boolean
    ): Flow<DataState<MessageResponse>>
}