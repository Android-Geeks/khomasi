package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemoteUserRepository

class GetUserFavoritePlaygroundsUseCase(
    private val remoteUserRepository: RemoteUserRepository

) {
    suspend operator fun invoke(userId: String) =
        remoteUserRepository.getUserFavouritePlaygrounds(userId)
}