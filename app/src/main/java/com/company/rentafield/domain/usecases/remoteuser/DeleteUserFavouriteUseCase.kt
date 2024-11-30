package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemotePlaygroundUserRepository

class DeleteUserFavouriteUseCase(
    private val remotePlaygroundUserRepository: RemotePlaygroundUserRepository
) {
    suspend operator fun invoke(token: String, playgroundId: Int, userId: String) =
        remotePlaygroundUserRepository.deleteUserFavouritePlayground(token, userId, playgroundId)
}