package com.company.khomasi.data.repository

import com.company.khomasi.data.data_source.remote.RetrofitService
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.BookingPlaygroundResponse
import com.company.khomasi.domain.model.BookingRequest
import com.company.khomasi.domain.model.FilteredPlaygroundResponse
import com.company.khomasi.domain.model.PlaygroundReviewsResponse
import com.company.khomasi.domain.repository.RemotePlaygroundRepository
import kotlinx.coroutines.flow.Flow

class RemotePlaygroundRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemotePlaygroundRepository {
    override suspend fun getFreeSlots(token: String, id: Int, dayDiff: Int) =
        handleApi { retrofitService.getOpenSlots(token, id, dayDiff) }

    override suspend fun getPlaygroundReviews(
        token: String,
        id: Int
    ): Flow<DataState<PlaygroundReviewsResponse>> =
        handleApi { retrofitService.getPlaygroundReviews(token, id) }

    override suspend fun getFilteredPlaygrounds(
        token: String,
        id: String,
        price: Int,
        type: Int,
        bookingTime: String,
        duration: Double
    ): Flow<DataState<FilteredPlaygroundResponse>> =
        handleApi {
            retrofitService.getFilteredPlaygrounds(
                token,
                id,
                price,
                type,
                bookingTime,
                duration
            )
        }

    override suspend fun bookingPlayground(
        token: String,
        body: BookingRequest
    ): Flow<DataState<BookingPlaygroundResponse>> = handleApi {
        retrofitService.bookingPlayground(token, body)
    }
}

