package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemotePlaygroundRepository

class GetFilteredPlaygroundsUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(
        token: String,
        id: String,
        price: Int,
        type: Int,
        bookingTime: String,
        duration: Double
    ) = remotePlaygroundRepository.getFilteredPlaygrounds(
        token = token,
        id = id,
        price = price,
        type = type,
        bookingTime = bookingTime,
        duration = duration
    )
}