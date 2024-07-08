package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.model.playground.BookingRequest
import com.company.rentafield.domain.repository.RemotePlaygroundRepository

class BookingPlaygroundUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(
        token: String,
        body: BookingRequest
    ) = remotePlaygroundRepository.bookingPlayground(token, body)
}