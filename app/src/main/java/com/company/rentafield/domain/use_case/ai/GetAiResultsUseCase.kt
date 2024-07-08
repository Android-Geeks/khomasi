package com.company.rentafield.domain.use_case.ai

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.ai.AiResponse
import com.company.rentafield.domain.repository.RemoteUserRepository
import kotlinx.coroutines.flow.Flow

class GetAiResultsUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        id: String
    ): Flow<DataState<AiResponse>> = remoteUserRepository.getAiResults(id)
}