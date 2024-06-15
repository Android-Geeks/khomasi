package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserRepository

class UserFavouriteUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, userId: String, playgroundId: Int) =
        remoteUserRepository.userFavourite(token, userId, playgroundId)
}