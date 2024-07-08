package com.company.rentafield.domain.use_case.ai

data class AiUseCases(
    val uploadVideoUseCase: UploadVideoUseCase,
    val getAiResultsUseCase: GetAiResultsUseCase,
    val getUploadStatusUseCase: GetUploadStatusUseCase
)