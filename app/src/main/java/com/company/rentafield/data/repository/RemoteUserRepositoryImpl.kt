package com.company.rentafield.data.repository


import com.company.rentafield.data.data_source.RetrofitService
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.user.FeedbackRequest
import com.company.rentafield.domain.model.user.UserUpdateData
import com.company.rentafield.domain.repository.RemoteUserRepository
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

    override suspend fun updateUser(token: String, userId: String, user: UserUpdateData) =
        handleApi { retrofitService.updateUser(token, userId, user) }


    override suspend fun sendFeedback(token: String, feedback: FeedbackRequest) =
        handleApi { retrofitService.sendFeedback(token, feedback) }

    override suspend fun getProfileImage(token: String, userId: String) =
        handleApi { retrofitService.getProfileImage(token, userId) }

    override suspend fun getUploadVideoStatus(id: String): Flow<DataState<MessageResponse>> =
        handleApi { retrofitService.getUploadVideoStatus(id) }

    override suspend fun userData(
        token: String,
        userId: String
    ) = handleApi {
        retrofitService.userData(token, userId)
    }

}