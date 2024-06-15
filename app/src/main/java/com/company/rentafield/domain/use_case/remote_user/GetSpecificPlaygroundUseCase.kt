package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserRepository

class GetSpecificPlaygroundUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, id: Int) =
        remoteUserRepository.getSpecificPlayground(token, id)
}