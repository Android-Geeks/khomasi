package com.company.khomasi.data.repository

import com.company.khomasi.data.data_source.remote.RetrofitService
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FilteredPlaygroundResponse
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import com.company.khomasi.domain.repository.RemotePlaygroundRepository
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime

class RemotePlaygroundRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemotePlaygroundRepository {
    override suspend fun getFreeSlots(token: String, id: Int, dayDiff: Int) =
        handleApi { retrofitService.getOpenSlots(token, id, dayDiff) }

    override suspend fun getPlaygroundReviews(
        token: String,
        id: Int
    ): Flow<DataState<PlaygroundReviewResponse>> =
        handleApi { retrofitService.getPlaygroundReviews(token, id) }

    override suspend fun getFilteredPlaygrounds(
        token: String,
        id: String,
        price: Int,
        bookingTime: LocalDateTime,
        duration: Double
    ): Flow<DataState<FilteredPlaygroundResponse>> =
        handleApi {
            retrofitService.getFilteredPlaygrounds(
                token,
                id,
                price,
                bookingTime,
                duration
            )
        }
}

