package com.company.rentafield.domain.repository

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.UserDataResponse
import com.company.rentafield.domain.model.user.FeedbackRequest
import com.company.rentafield.domain.model.user.ProfileImageResponse
import com.company.rentafield.domain.model.user.UserUpdateData
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface RemoteUserRepository {
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

    suspend fun getUploadVideoStatus(
        id: String
    ): Flow<DataState<MessageResponse>>

    suspend fun userData(
        token: String,
        userId: String
    ): Flow<DataState<UserDataResponse>>
}