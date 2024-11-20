package com.company.rentafield.data.repositories.remoteuser.playground

import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow

interface RemoteUserPlayground {
    suspend fun deleteUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ): Flow<DataState<com.company.rentafield.data.models.MessageResponse>>

    suspend fun getUserFavouritePlaygrounds(
        token: String,
        userId: String
    ): Flow<DataState<com.company.rentafield.data.models.favourite.FavouritePlaygroundResponse>>

    suspend fun addUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ): Flow<DataState<com.company.rentafield.data.models.MessageResponse>>

    suspend fun getSpecificPlayground(
        token: String,
        id: Int
    ): Flow<DataState<com.company.rentafield.data.models.playground.PlaygroundScreenResponse>>

    suspend fun addPlaygroundReview(
        token: String,
        playgroundReview: com.company.rentafield.data.models.booking.PlaygroundReviewRequest
    ): Flow<DataState<com.company.rentafield.data.models.MessageResponse>>

}