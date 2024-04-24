package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.model.FeedbackRequest
import com.company.khomasi.domain.repository.RemoteUserRepository

class SendFeedbackUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(token: String, feedback: FeedbackRequest) =
        remoteUserRepository.sendFeedback(token, feedback)
}