package com.company.rentafield.data.repositories.remoteuser.user


import com.company.rentafield.data.services.RetrofitUserService
import com.company.rentafield.domain.DataState
import com.company.rentafield.utils.handleApi
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class RemoteUserRepositoryImpl(
    private val retrofitUserService: RetrofitUserService
) : RemoteUserRepository {

    override suspend fun uploadProfilePicture(
        token: String,
        userId: String,
        picture: MultipartBody.Part
    ) =
        handleApi { retrofitUserService.uploadProfilePicture(token, userId, picture) }

    override suspend fun updateUser(
        token: String,
        userId: String,
        user: com.company.rentafield.domain.models.user.UserUpdateData
    ) =
        handleApi { retrofitUserService.updateUser(token, userId, user) }


    override suspend fun sendFeedback(
        token: String,
        feedback: com.company.rentafield.domain.models.user.FeedbackRequest
    ) =
        handleApi { retrofitUserService.sendFeedback(token, feedback) }

    override suspend fun getProfileImage(token: String, userId: String) =
        handleApi { retrofitUserService.getProfileImage(token, userId) }

    override suspend fun getUploadVideoStatus(id: String): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>> =
        handleApi { retrofitUserService.getUploadVideoStatus(id) }

    override suspend fun userData(
        token: String,
        userId: String
    ) = handleApi {
        retrofitUserService.userData(token, userId)
    }

}