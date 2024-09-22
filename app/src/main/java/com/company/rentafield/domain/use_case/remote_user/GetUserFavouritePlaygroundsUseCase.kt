package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserPlayground

class GetUserFavoritePlaygroundsUseCase(
    private val remoteUserPlayground: RemoteUserPlayground

) {
    suspend operator fun invoke(token:String,userId: String) =
        remoteUserPlayground.getUserFavouritePlaygrounds(token,userId)
}