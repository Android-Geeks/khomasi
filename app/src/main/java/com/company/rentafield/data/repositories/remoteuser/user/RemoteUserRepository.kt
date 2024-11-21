package com.company.rentafield.data.repositories.remoteuser.user

import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface RemoteUserRepository {
    suspend fun uploadProfilePicture(
        token: String,
        userId: String,
        picture: MultipartBody.Part
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>>

    suspend fun updateUser(
        token: String,
        userId: String,
        user: com.company.rentafield.domain.models.user.UserUpdateData
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>>

    suspend fun sendFeedback(
        token: String,
        feedback: com.company.rentafield.domain.models.user.FeedbackRequest
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>>

    suspend fun getProfileImage(
        token: String,
        userId: String
    ): Flow<DataState<com.company.rentafield.domain.models.user.ProfileImageResponse>>

    suspend fun getUploadVideoStatus(
        id: String
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>>

    suspend fun userData(
        token: String,
        userId: String
    ): Flow<DataState<com.company.rentafield.domain.models.UserDataResponse>>
}