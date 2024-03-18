package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemoteUserRepository

class GetUserBookingsUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(id:String) =
        remoteUserRepository.getUserBookings(id)
}