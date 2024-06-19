package com.company.rentafield.domain.repository

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.FavouritePlaygroundResponse
import com.company.rentafield.domain.model.FeedbackRequest
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.MyBookingsResponse
import com.company.rentafield.domain.model.PlaygroundReviewRequest
import com.company.rentafield.domain.model.PlaygroundScreenResponse
import com.company.rentafield.domain.model.PlaygroundsResponse
import com.company.rentafield.domain.model.ProfileImageResponse
import com.company.rentafield.domain.model.UserLoginResponse
import com.company.rentafield.domain.model.UserRegisterData
import com.company.rentafield.domain.model.UserRegisterResponse
import com.company.rentafield.domain.model.UserUpdateData
import com.company.rentafield.domain.model.VerificationResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

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
    suspend fun getUserBookings(token: String, id: String): Flow<DataState<MyBookingsResponse>>
    suspend fun deleteUserFavourite(
        token: String,
        userId: String,
        playgroundId: Int
    ): Flow<DataState<MessageResponse>>

    suspend fun getUserFavouritePlaygrounds(
        token: String,
        userId: String
    ): Flow<DataState<FavouritePlaygroundResponse>>

    suspend fun userFavourite(
        token: String,
        userId: String,
        playgroundId: Int
    ): Flow<DataState<MessageResponse>>

    suspend fun cancelBooking(
        token: String,
        bookingId: Int,
        isUser: Boolean
    ): Flow<DataState<MessageResponse>>

    suspend fun getSpecificPlayground(
        token: String,
        id: Int
    ): Flow<DataState<PlaygroundScreenResponse>>

    suspend fun uploadProfilePicture(
        token: String,
        userId: String,
        picture: MultipartBody.Part
    ): Flow<DataState<MessageResponse>>

    suspend fun updateUser(
        token: String,
        userId: String,
        user: UserUpdateData
    ): Flow<DataState<MessageResponse>>

    suspend fun sendFeedback(
        token: String,
        feedback: FeedbackRequest
    ): Flow<DataState<MessageResponse>>

    suspend fun getProfileImage(
        token: String,
        userId: String
    ): Flow<DataState<ProfileImageResponse>>

    suspend fun playgroundReview(
        token: String,
        playgroundReview: PlaygroundReviewRequest
    ): Flow<DataState<MessageResponse>>
}