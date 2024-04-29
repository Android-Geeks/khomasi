package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.model.PlaygroundReviewResponse
import com.company.khomasi.domain.repository.RemoteUserRepository

class PlaygroundReviewUseCase(
    private val remoteUserRepository: RemoteUserRepository

) {
    suspend operator fun invoke(
        playgroundReview: PlaygroundReviewResponse, token: String,
    ) =
        remoteUserRepository.playgroundReview(token, playgroundReview)
}