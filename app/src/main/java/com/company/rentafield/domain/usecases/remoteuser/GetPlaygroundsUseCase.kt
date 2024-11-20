package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepository

class GetPlaygroundsUseCase(
    private val remotePlaygroundsRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(token: String, userId: String) =
        remotePlaygroundsRepository.getPlaygrounds(token, userId)
}
