package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemotePlaygroundUserRepository

class PlaygroundReviewUseCase(
    private val remotePlaygroundUserRepository: RemotePlaygroundUserRepository

) {
    suspend operator fun invoke(
        playgroundReview: com.company.rentafield.domain.models.booking.PlaygroundReviewRequest,
        token: String,
    ) =
        remotePlaygroundUserRepository.addPlaygroundReview(token, playgroundReview)
}