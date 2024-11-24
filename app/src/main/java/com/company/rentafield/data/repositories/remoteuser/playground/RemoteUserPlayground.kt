package com.company.rentafield.data.repositories.remoteuser.playground

import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow

interface RemoteUserPlayground {
    suspend fun deleteUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>>

    suspend fun getUserFavouritePlaygrounds(
        token: String,
        userId: String
    ): Flow<DataState<com.company.rentafield.domain.models.favourite.FavouritePlaygroundResponse>>

    suspend fun addUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>>

    suspend fun addPlaygroundReview(
        token: String,
        playgroundReview: com.company.rentafield.domain.models.booking.PlaygroundReviewRequest
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>>

}