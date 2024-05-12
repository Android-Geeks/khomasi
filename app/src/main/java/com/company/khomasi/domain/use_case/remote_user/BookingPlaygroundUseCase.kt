package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.model.BookingRequest
import com.company.khomasi.domain.repository.RemotePlaygroundRepository

class BookingPlaygroundUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(
        token: String,
        body: BookingRequest
    ) = remotePlaygroundRepository.bookingPlayground(token, body)
}