package com.company.rentafield.domain.use_case.ai

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.repository.RemoteAiRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadVideoUseCase(
    private val remoteAiRepository: RemoteAiRepository
) {
    suspend operator fun invoke(
        id: RequestBody,
        video: MultipartBody.Part
    ): Flow<DataState<MessageResponse>> = remoteAiRepository.uploadVideo(id, video)
}
