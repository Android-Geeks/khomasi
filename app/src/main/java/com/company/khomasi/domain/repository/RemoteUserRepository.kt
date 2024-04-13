package com.company.khomasi.domain.repository

import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.MessageResponse
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.model.UserBookingsResponse
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.domain.model.UserUpdateData
import com.company.khomasi.domain.model.VerificationResponse
import kotlinx.coroutines.flow.Flow

interface RemoteUserRepository {
    suspend fun registerUser(userRegisterData: UserRegisterData): Flow<DataState<UserRegisterResponse>>
    suspend fun loginUser(email: String, password: String): Flow<DataState<UserLoginResponse>>
    suspend fun getVerificationCode(email: String): Flow<DataState<VerificationResponse>>
    suspend fun confirmEmail(email: String, code: String): Flow<DataState<MessageResponse>>
    suspend fun recoverAccount(
        email: String,
        code: String,
        newPassword: String
    ): Flow<DataState<MessageResponse>>

    suspend fun getPlaygrounds(token: String, userId: String): Flow<DataState<PlaygroundsResponse>>
    suspend fun getUserBookings(id: String): Flow<DataState<UserBookingsResponse>>
    suspend fun deleteUserFavourite(
        userId: String,
        playgroundId: String
    ): Flow<DataState<MessageResponse>>

    suspend fun getUserFavouritePlaygrounds(userId: String): Flow<DataState<FavouritePlaygroundResponse>>
    suspend fun userFavourite(
        userId: String,
        playgroundId: String
    ): Flow<DataState<MessageResponse>>

    suspend fun getSpecificPlayground(
        token: String,
        id: Int
    ): Flow<DataState<PlaygroundScreenResponse>>

    suspend fun uploadProfilePicture(
        token: String,
        userId: String,
        picture: String
    ): Flow<DataState<MessageResponse>>

    suspend fun updateUser(
        token: String,
        userId: String,
        user: UserUpdateData
    ): Flow<DataState<MessageResponse>>

}