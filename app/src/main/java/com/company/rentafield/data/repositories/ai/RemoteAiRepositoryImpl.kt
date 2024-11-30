package com.company.rentafield.data.repositories.ai

import com.company.rentafield.data.services.RetrofitAiService
import com.company.rentafield.data.services.RetrofitUserService
import com.company.rentafield.domain.DataState
import com.company.rentafield.utils.handleApi
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteAiRepositoryImpl @Inject constructor(
    private val retrofitAiService: RetrofitAiService,
    private val retrofitUserService: RetrofitUserService
) : RemoteAiRepository {

    override suspend fun getAiResults(id: String): Flow<DataState<com.company.rentafield.domain.models.ai.AiResponse>> =
        handleApi { retrofitUserService.getAiResults(id) }

    override suspend fun uploadVideo(
        id: RequestBody,
        video: MultipartBody.Part
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>> =
        handleApi { retrofitAiService.uploadVideo(id, video) }
}