package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.model.user.UserUpdateData
import com.company.rentafield.domain.repository.RemoteUserRepository

class UpdateUserUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        token: String,
        userId: String,
        user: UserUpdateData
    ) = remoteUserRepository.updateUser(token, userId, user)
}