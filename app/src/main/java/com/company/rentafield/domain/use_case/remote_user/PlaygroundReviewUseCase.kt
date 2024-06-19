package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.model.PlaygroundReviewRequest
import com.company.rentafield.domain.repository.RemoteUserRepository

class PlaygroundReviewUseCase(
    private val remoteUserRepository: RemoteUserRepository

) {
    suspend operator fun invoke(
        playgroundReview: PlaygroundReviewRequest, token: String,
    ) =
        remoteUserRepository.playgroundReview(token, playgroundReview)
}