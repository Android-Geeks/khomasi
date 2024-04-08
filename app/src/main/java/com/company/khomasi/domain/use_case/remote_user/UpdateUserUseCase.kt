package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.model.UserUpdateData
import com.company.khomasi.domain.repository.RemoteUserRepository

class UpdateUserUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        token: String,
        userId: String,
        user: UserUpdateData
    ) = remoteUserRepository.updateUser(token, userId, user)
}