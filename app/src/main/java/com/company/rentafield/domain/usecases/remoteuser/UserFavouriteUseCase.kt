package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemoteUserPlayground

class UserFavouriteUseCase(
    private val remoteUserPlayground: RemoteUserPlayground
) {
    suspend operator fun invoke(token: String, userId: String, playgroundId: Int) =
        remoteUserPlayground.addUserFavouritePlayground(token, userId, playgroundId)
}