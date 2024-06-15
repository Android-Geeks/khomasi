package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserRepository

class DeleteUserFavouriteUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, playgroundId: Int, userId: String) =
        remoteUserRepository.deleteUserFavourite(token, userId, playgroundId)
}