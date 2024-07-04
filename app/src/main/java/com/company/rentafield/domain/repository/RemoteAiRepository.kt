package com.company.rentafield.domain.repository

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RemoteAiRepository {
    suspend fun uploadVideo(
        id: RequestBody,
        video: MultipartBody.Part
    ): Flow<DataState<MessageResponse>>
}