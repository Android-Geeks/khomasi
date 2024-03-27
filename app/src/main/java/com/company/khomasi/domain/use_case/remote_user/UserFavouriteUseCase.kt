package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemoteUserRepository

class UserFavouriteUseCase(
   private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token:String,userId:String,playgroundId:String)=
        remoteUserRepository.userFavourite(token,userId,playgroundId)
}