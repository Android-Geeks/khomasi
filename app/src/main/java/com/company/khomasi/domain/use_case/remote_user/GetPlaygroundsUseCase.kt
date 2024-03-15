package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemoteUserRepository

class GetPlaygroundsUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, userId: String) =
        remoteUserRepository.getPlaygrounds(token, userId)
}
