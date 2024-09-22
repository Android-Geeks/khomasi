package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemotePlaygroundRepository

class GetPlaygroundsUseCase(
    private val remotePlaygroundsRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(token: String, userId: String) =
        remotePlaygroundsRepository.getPlaygrounds(token, userId)
}
