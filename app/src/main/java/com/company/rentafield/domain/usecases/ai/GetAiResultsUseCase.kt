package com.company.rentafield.domain.usecases.ai

import com.company.rentafield.data.repositories.ai.RemoteAiRepository
import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow

class GetAiResultsUseCase(
    private val remoteAiRepository: RemoteAiRepository
) {
    suspend operator fun invoke(
        id: String
    ): Flow<DataState<com.company.rentafield.domain.models.ai.AiResponse>> =
        remoteAiRepository.getAiResults(id)
}