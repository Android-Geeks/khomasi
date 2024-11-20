package com.company.rentafield.data.repositories.remoteuser.user


import com.company.rentafield.data.datasource.RetrofitService
import com.company.rentafield.domain.DataState
import com.company.rentafield.utils.handleApi
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class RemoteUserRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemoteUserRepository {

    override suspend fun uploadProfilePicture(
        token: String,
        userId: String,
        picture: MultipartBody.Part
    ) =
        handleApi { retrofitService.uploadProfilePicture(token, userId, picture) }

    override suspend fun updateUser(
        token: String,
        userId: String,
        user: com.company.rentafield.data.models.user.UserUpdateData
    ) =
        handleApi { retrofitService.updateUser(token, userId, user) }


    override suspend fun sendFeedback(
        token: String,
        feedback: com.company.rentafield.data.models.user.FeedbackRequest
    ) =
        handleApi { retrofitService.sendFeedback(token, feedback) }

    override suspend fun getProfileImage(token: String, userId: String) =
        handleApi { retrofitService.getProfileImage(token, userId) }

    override suspend fun getUploadVideoStatus(id: String): Flow<DataState<com.company.rentafield.data.models.MessageResponse>> =
        handleApi { retrofitService.getUploadVideoStatus(id) }

    override suspend fun userData(
        token: String,
        userId: String
    ) = handleApi {
        retrofitService.userData(token, userId)
    }

}