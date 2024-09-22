package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserBooking

class CancelBookingUseCase(
    private val remoteUserBooking: RemoteUserBooking
) {
    suspend operator fun invoke(token: String, bookingId: Int, isUser: Boolean) =
        remoteUserBooking.cancelBooking(token, bookingId, isUser)
}