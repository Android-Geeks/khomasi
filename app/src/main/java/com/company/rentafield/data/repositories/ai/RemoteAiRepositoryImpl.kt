package com.company.rentafield.data.repositories.ai

import com.company.rentafield.data.services.RetrofitAiService
import com.company.rentafield.data.services.RetrofitService
import com.company.rentafield.domain.DataState
import com.company.rentafield.utils.handleApi
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteAiRepositoryImpl(
    private val retrofitAiService: RetrofitAiService,
    private val retrofitService: RetrofitService
) : RemoteAiRepository {

    override suspend fun getAiResults(id: String): Flow<DataState<com.company.rentafield.domain.models.ai.AiResponse>> =
        handleApi { retrofitService.getAiResults(id) }

    override suspend fun uploadVideo(
        id: RequestBody,
        video: MultipartBody.Part
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>> =
        handleApi { retrofitAiService.uploadVideo(id, video) }
}