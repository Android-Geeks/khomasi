package com.company.rentafield.data.repository

import com.company.rentafield.data.data_source.RetrofitService
import com.company.rentafield.domain.repository.RemoteUserBooking
import com.company.rentafield.utils.handleApi

class RemoteUserBookingImpl(
    private val retrofitService: RetrofitService
) : RemoteUserBooking {

    override suspend fun getUserBookings(token: String, id: String) =
        handleApi { retrofitService.getUserBookings(token, id) }

    override suspend fun cancelBooking(
        token: String,
        bookingId: Int,
        isUser: Boolean
    ) = handleApi { retrofitService.cancelBooking(token, bookingId, isUser) }
}