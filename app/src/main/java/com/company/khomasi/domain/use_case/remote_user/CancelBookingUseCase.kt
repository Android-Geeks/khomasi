package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemoteUserRepository

class CancelBookingUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, bookingId: Int, isUser: Boolean) =
        remoteUserRepository.cancelBooking(token, bookingId, isUser)
}