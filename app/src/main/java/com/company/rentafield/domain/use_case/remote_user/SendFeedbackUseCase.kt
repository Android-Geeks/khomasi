package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.model.user.FeedbackRequest
import com.company.rentafield.domain.repository.RemoteUserRepository

class SendFeedbackUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, feedback: FeedbackRequest) =
        remoteUserRepository.sendFeedback(token, feedback)
}