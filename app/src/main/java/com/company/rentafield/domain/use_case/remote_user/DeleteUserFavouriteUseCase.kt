package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserPlayground

class DeleteUserFavouriteUseCase(
    private val remoteUserPlayground: RemoteUserPlayground
) {
    suspend operator fun invoke(token: String, playgroundId: Int, userId: String) =
        remoteUserPlayground.deleteUserFavouritePlayground(token, userId, playgroundId)
}