package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemoteUserPlayground

class PlaygroundReviewUseCase(
    private val remoteUserPlayground: RemoteUserPlayground

) {
    suspend operator fun invoke(
        playgroundReview: com.company.rentafield.data.models.booking.PlaygroundReviewRequest,
        token: String,
    ) =
        remoteUserPlayground.addPlaygroundReview(token, playgroundReview)
}