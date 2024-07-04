package com.company.rentafield.data.repository

import com.company.rentafield.data.data_source.RetrofitAiService
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.repository.RemoteAiRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteAiRepositoryImpl(
    private val retrofitAiService: RetrofitAiService
) : RemoteAiRepository {
    override suspend fun uploadVideo(
        id: RequestBody,
        video: MultipartBody.Part
    ): Flow<DataState<MessageResponse>> = handleApi {
        retrofitAiService.uploadVideo(id, video)
    }
}