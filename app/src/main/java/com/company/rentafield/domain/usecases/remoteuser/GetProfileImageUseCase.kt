package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.user.RemoteUserRepository

class GetProfileImageUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, userId: String) =
        remoteUserRepository.getProfileImage(token, userId)
}