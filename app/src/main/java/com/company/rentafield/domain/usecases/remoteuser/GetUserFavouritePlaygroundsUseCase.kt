package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemotePlaygroundUserRepository

class GetUserFavoritePlaygroundsUseCase(
    private val remotePlaygroundUserRepository: RemotePlaygroundUserRepository

) {
    suspend operator fun invoke(token: String, userId: String) =
        remotePlaygroundUserRepository.getUserFavouritePlaygrounds(token, userId)
}