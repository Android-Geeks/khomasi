package com.company.rentafield.data.repositories.remoteuser.playground

import com.company.rentafield.data.services.RetrofitUserService
import com.company.rentafield.utils.handleApi

class RemoteUserPlaygroundImpl(
    private val retrofitUserService: RetrofitUserService
) : RemoteUserPlayground {

    override suspend fun deleteUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ) =
        handleApi { retrofitUserService.deleteUserFavouritePlayground(token, userId, playgroundId) }

    override suspend fun getUserFavouritePlaygrounds(token: String, userId: String) =
        handleApi { retrofitUserService.getUserFavouritePlaygrounds(token, userId) }

    override suspend fun addUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ) =
        handleApi { retrofitUserService.addUserFavouritePlayground(token, userId, playgroundId) }

    override suspend fun addPlaygroundReview(
        token: String,
        playgroundReview: com.company.rentafield.domain.models.booking.PlaygroundReviewRequest
    ) = handleApi { retrofitUserService.addPlaygroundReview(token, playgroundReview) }
}