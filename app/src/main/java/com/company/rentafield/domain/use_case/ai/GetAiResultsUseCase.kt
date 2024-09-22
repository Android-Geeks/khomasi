package com.company.rentafield.domain.use_case.ai

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.ai.AiResponse
import com.company.rentafield.domain.repository.RemoteAiRepository
import kotlinx.coroutines.flow.Flow

class GetAiResultsUseCase(
    private val remoteAiRepository: RemoteAiRepository
) {
    suspend operator fun invoke(
        id: String
    ): Flow<DataState<AiResponse>> = remoteAiRepository.getAiResults(id)
}