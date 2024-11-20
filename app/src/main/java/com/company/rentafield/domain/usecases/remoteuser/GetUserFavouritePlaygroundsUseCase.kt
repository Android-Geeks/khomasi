package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemoteUserPlayground

class GetUserFavoritePlaygroundsUseCase(
    private val remoteUserPlayground: RemoteUserPlayground

) {
    suspend operator fun invoke(token: String, userId: String) =
        remoteUserPlayground.getUserFavouritePlaygrounds(token, userId)
}