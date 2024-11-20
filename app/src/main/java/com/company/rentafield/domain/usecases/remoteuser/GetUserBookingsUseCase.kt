package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.booking.RemoteUserBooking

class GetUserBookingsUseCase(
    private val remoteUserBooking: RemoteUserBooking
) {
    suspend operator fun invoke(token: String, id: String) =
        remoteUserBooking.getUserBookings(token, id)
}