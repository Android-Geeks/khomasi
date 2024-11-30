package com.company.rentafield.data.repositories.booking

import com.company.rentafield.data.services.RetrofitUserService
import com.company.rentafield.utils.handleApi
import javax.inject.Inject

class RemoteUserBookingImpl @Inject constructor(
    private val retrofitUserService: RetrofitUserService
) : RemoteUserBooking {

    override suspend fun getUserBookings(token: String, id: String) =
        handleApi { retrofitUserService.getUserBookings(token, id) }

    override suspend fun cancelBooking(
        token: String,
        bookingId: Int,
        isUser: Boolean
    ) = handleApi { retrofitUserService.cancelBooking(token, bookingId, isUser) }
}