package com.company.rentafield.data.repositories.playground

import com.company.rentafield.data.services.RetrofitPlaygroundService
import com.company.rentafield.domain.DataState
import com.company.rentafield.utils.handleApi
import kotlinx.coroutines.flow.Flow

class RemotePlaygroundRepositoryImpl(
    private val retrofitPlaygroundService: RetrofitPlaygroundService
) : RemotePlaygroundRepository {

    override suspend fun getPlaygrounds(token: String, userId: String) =
        handleApi { retrofitPlaygroundService.getPlaygrounds(token, userId) }

    override suspend fun getFreeSlots(token: String, id: Int, dayDiff: Int) =
        handleApi { retrofitPlaygroundService.getOpenSlots(token, id, dayDiff) }

    override suspend fun getPlaygroundReviews(
        token: String,
        id: Int
    ): Flow<DataState<com.company.rentafield.domain.models.playground.PlaygroundReviewsResponse>> =
        handleApi { retrofitPlaygroundService.getPlaygroundReviews(token, id) }

    override suspend fun getFilteredPlaygrounds(
        token: String,
        id: String,
        price: Int,
        type: Int,
        bookingTime: String,
        duration: Double
    ): Flow<DataState<com.company.rentafield.domain.models.search.FilteredPlaygroundResponse>> =
        handleApi {
            retrofitPlaygroundService.getFilteredPlaygrounds(
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
            retrofitPlaygroundService.bookingPlayground(token, body)
        }

    override suspend fun getSpecificPlayground(token: String, id: Int) =
        handleApi { retrofitPlaygroundService.getSpecificPlayground(token, id) }
}

