package com.company.rentafield.domain.usecases.ai

data class AiUseCases(
    val uploadVideoUseCase: UploadVideoUseCase,
    val getAiResultsUseCase: GetAiResultsUseCase,
    val getUploadStatusUseCase: GetUploadStatusUseCase
)