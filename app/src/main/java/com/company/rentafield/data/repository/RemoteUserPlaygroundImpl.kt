package com.company.rentafield.data.repository

import com.company.rentafield.data.data_source.RetrofitService
import com.company.rentafield.domain.model.booking.PlaygroundReviewRequest
import com.company.rentafield.domain.repository.RemoteUserPlayground
import com.company.rentafield.utils.handleApi

class RemoteUserPlaygroundImpl(
    private val retrofitService: RetrofitService
) : RemoteUserPlayground {

    override suspend fun getSpecificPlayground(token: String, id: Int) =
        handleApi { retrofitService.getSpecificPlayground(token, id) }

    override suspend fun deleteUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ) =
        handleApi { retrofitService.deleteUserFavouritePlayground(token, userId, playgroundId) }

    override suspend fun getUserFavouritePlaygrounds(token: String, userId: String) =
        handleApi { retrofitService.getUserFavouritePlaygrounds(token, userId) }

    override suspend fun addUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ) =
        handleApi { retrofitService.addUserFavouritePlayground(token, userId, playgroundId) }

    override suspend fun addPlaygroundReview(
        token: String,
        playgroundReview: PlaygroundReviewRequest
    ) = handleApi { retrofitService.addPlaygroundReview(token, playgroundReview) }
}