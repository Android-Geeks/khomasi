package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemotePlaygroundUserRepository

class UserFavouriteUseCase(
    private val remotePlaygroundUserRepository: RemotePlaygroundUserRepository
) {
    suspend operator fun invoke(token: String, userId: String, playgroundId: Int) =
        remotePlaygroundUserRepository.addUserFavouritePlayground(token, userId, playgroundId)
}