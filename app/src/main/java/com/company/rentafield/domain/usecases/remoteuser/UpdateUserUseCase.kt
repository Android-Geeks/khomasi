package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.user.RemoteUserRepository

class UpdateUserUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        token: String,
        userId: String,
        user: com.company.rentafield.data.models.user.UserUpdateData
    ) = remoteUserRepository.updateUser(token, userId, user)
}