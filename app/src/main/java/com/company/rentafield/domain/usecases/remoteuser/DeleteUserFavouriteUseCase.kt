package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemoteUserPlayground

class DeleteUserFavouriteUseCase(
    private val remoteUserPlayground: RemoteUserPlayground
) {
    suspend operator fun invoke(token: String, playgroundId: Int, userId: String) =
        remoteUserPlayground.deleteUserFavouritePlayground(token, userId, playgroundId)
}