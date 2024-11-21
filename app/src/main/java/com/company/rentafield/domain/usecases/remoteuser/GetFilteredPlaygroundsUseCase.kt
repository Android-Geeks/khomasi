package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepository

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