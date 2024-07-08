package com.company.rentafield.domain.use_case.ai

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.repository.RemoteUserRepository
import kotlinx.coroutines.flow.Flow


class GetUploadStatusUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        id: String
    ): Flow<DataState<MessageResponse>> = remoteUserRepository.getUploadVideoStatus(id)
}