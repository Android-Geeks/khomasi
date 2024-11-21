package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.booking.RemoteUserBooking

class CancelBookingUseCase(
    private val remoteUserBooking: RemoteUserBooking
) {
    suspend operator fun invoke(token: String, bookingId: Int, isUser: Boolean) =
        remoteUserBooking.cancelBooking(token, bookingId, isUser)
}