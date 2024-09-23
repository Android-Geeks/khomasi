package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.model.booking.PlaygroundReviewRequest
import com.company.rentafield.domain.repository.RemoteUserPlayground

class PlaygroundReviewUseCase(
    private val remoteUserPlayground: RemoteUserPlayground

) {
    suspend operator fun invoke(
        playgroundReview: PlaygroundReviewRequest, token: String,
    ) =
        remoteUserPlayground.addPlaygroundReview(token, playgroundReview)
}