package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserBooking

class GetUserBookingsUseCase(
    private val remoteUserBooking: RemoteUserBooking
) {
    suspend operator fun invoke(token:String,id:String) =
        remoteUserBooking.getUserBookings(token,id)
}