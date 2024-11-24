package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepository

class GetSpecificPlaygroundUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(token: String, id: Int) =
        remotePlaygroundRepository.getSpecificPlayground(token, id)
}