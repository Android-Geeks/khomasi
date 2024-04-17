package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemoteUserRepository

class GetFreeTimeSlotsUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, id: Int, dayDiff: Int) =
        remoteUserRepository.getFreeSlots(token = token, id = id, dayDiff = dayDiff)
}


