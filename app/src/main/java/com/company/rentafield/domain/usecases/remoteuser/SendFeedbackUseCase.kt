package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.user.RemoteUserRepository

class SendFeedbackUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        token: String,
        feedback: com.company.rentafield.domain.models.user.FeedbackRequest
    ) =
        remoteUserRepository.sendFeedback(token, feedback)
}