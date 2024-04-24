package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemotePlaygroundRepository

class GetFreeTimeSlotsUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(token: String, id: Int, dayDiff: Int) =
        remotePlaygroundRepository.getFreeSlots(token = token, id = id, dayDiff = dayDiff)
}


