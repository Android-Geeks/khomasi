package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemoteUserRepository

class DeleteUserFavouriteUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, playgroundId: String, userId: String)=
        remoteUserRepository.deleteUserFavourite(token,userId,playgroundId)
}