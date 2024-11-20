package com.company.rentafield.data.repositories.ai

import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RemoteAiRepository {
    suspend fun getAiResults(id: String): Flow<DataState<com.company.rentafield.data.models.ai.AiResponse>>
    suspend fun uploadVideo(
        id: RequestBody,
        video: MultipartBody.Part
    ): Flow<DataState<com.company.rentafield.data.models.MessageResponse>>
}