package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepository

class GetFreeTimeSlotsUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(token: String, id: Int, dayDiff: Int) =
        remotePlaygroundRepository.getFreeSlots(token = token, id = id, dayDiff = dayDiff)
}


