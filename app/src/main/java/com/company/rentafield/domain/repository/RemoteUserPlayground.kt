package com.company.rentafield.domain.repository

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.booking.PlaygroundReviewRequest
import com.company.rentafield.domain.model.favourite.FavouritePlaygroundResponse
import com.company.rentafield.domain.model.playground.PlaygroundScreenResponse
import kotlinx.coroutines.flow.Flow

interface RemoteUserPlayground {
    suspend fun deleteUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ): Flow<DataState<MessageResponse>>

    suspend fun getUserFavouritePlaygrounds(
        token: String,
        userId: String
    ): Flow<DataState<FavouritePlaygroundResponse>>

    suspend fun addUserFavouritePlayground(
        token: String,
        userId: String,
        playgroundId: Int
    ): Flow<DataState<MessageResponse>>

    suspend fun getSpecificPlayground(
        token: String,
        id: Int
    ): Flow<DataState<PlaygroundScreenResponse>>

    suspend fun addPlaygroundReview(
        token: String,
        playgroundReview: PlaygroundReviewRequest
    ): Flow<DataState<MessageResponse>>

}