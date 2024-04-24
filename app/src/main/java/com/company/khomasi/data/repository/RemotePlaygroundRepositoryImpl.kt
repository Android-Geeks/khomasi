package com.company.khomasi.data.repository

import com.company.khomasi.data.data_source.remote.RetrofitService
import com.company.khomasi.domain.repository.RemotePlaygroundRepository

class RemotePlaygroundRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemotePlaygroundRepository {
    override suspend fun getFreeSlots(token: String, id: Int, dayDiff: Int) =
        handleApi { retrofitService.getOpenSlots(token, id, dayDiff) }
}

