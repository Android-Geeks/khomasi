package com.company.rentafield.data.repositories.playground

import com.company.rentafield.data.datasource.RetrofitService
import com.company.rentafield.domain.DataState
import com.company.rentafield.utils.handleApi
import kotlinx.coroutines.flow.Flow

class RemotePlaygroundRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemotePlaygroundRepository {

    override suspend fun getPlaygrounds(token: String, userId: String) =
        handleApi { retrofitService.getPlaygrounds(token, userId) }

    override suspend fun getFreeSlots(token: String, id: Int, dayDiff: Int) =
        handleApi { retrofitService.getOpenSlots(token, id, dayDiff) }

    override suspend fun getPlaygroundReviews(
        token: String,
        id: Int
    ): Flow<DataState<com.company.rentafield.domain.models.playground.PlaygroundReviewsResponse>> =
        handleApi { retrofitService.getPlaygroundReviews(token, id) }

    override suspend fun getFilteredPlaygrounds(
        token: String,
        id: String,
        price: Int,
        type: Int,
        bookingTime: String,
        duration: Double
    ): Flow<DataState<com.company.rentafield.domain.models.search.FilteredPlaygroundResponse>> =
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
        body: com.company.rentafield.domain.models.playground.BookingRequest
    ): Flow<DataState<com.company.rentafield.domain.models.booking.BookingPlaygroundResponse>> =
        handleApi {
            retrofitService.bookingPlayground(token, body)
        }
}

