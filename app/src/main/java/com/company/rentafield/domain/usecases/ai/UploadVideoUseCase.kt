package com.company.rentafield.domain.usecases.ai

import com.company.rentafield.data.repositories.ai.RemoteAiRepository
import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadVideoUseCase(
    private val remoteAiRepository: RemoteAiRepository
) {
    suspend operator fun invoke(
        id: RequestBody,
        video: MultipartBody.Part
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>> =
        remoteAiRepository.uploadVideo(id, video)
}
