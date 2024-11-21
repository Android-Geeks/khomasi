package com.company.rentafield.domain.usecases.ai

import com.company.rentafield.data.repositories.remoteuser.user.RemoteUserRepository
import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow


class GetUploadStatusUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        id: String
    ): Flow<DataState<com.company.rentafield.domain.models.MessageResponse>> =
        remoteUserRepository.getUploadVideoStatus(id)
}