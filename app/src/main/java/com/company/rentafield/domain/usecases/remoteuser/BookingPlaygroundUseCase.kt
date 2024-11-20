package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepository

class BookingPlaygroundUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(
        token: String,
        body: com.company.rentafield.data.models.playground.BookingRequest
    ) = remotePlaygroundRepository.bookingPlayground(token, body)
}