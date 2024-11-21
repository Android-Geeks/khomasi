package com.company.rentafield.data.repositories.remoteuser.playground

import com.company.rentafield.data.datasource.RetrofitService
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
        playgroundReview: com.company.rentafield.domain.models.booking.PlaygroundReviewRequest
    ) = handleApi { retrofitService.addPlaygroundReview(token, playgroundReview) }
}