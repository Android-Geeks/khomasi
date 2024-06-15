package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.model.PlaygroundReviewResponse
import com.company.rentafield.domain.repository.RemoteUserRepository

class PlaygroundReviewUseCase(
    private val remoteUserRepository: RemoteUserRepository

) {
    suspend operator fun invoke(
        playgroundReview: PlaygroundReviewResponse, token: String,
    ) =
        remoteUserRepository.playgroundReview(token, playgroundReview)
}