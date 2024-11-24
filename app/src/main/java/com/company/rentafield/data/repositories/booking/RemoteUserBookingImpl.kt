package com.company.rentafield.data.repositories.booking

import com.company.rentafield.data.services.RetrofitService
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