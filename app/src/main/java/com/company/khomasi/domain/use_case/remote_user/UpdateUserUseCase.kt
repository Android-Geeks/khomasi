package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.repository.RemoteUserRepository

class UpdateUserUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        token: String,
        userId: String,
        user: UserRegisterData
    ) = remoteUserRepository.updateUser(token, userId, user)
}