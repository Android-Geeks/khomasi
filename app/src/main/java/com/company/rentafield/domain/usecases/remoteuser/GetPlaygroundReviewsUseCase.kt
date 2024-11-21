package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepository

class GetPlaygroundReviewsUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(token: String, id: Int) =
        remotePlaygroundRepository.getPlaygroundReviews(token = token, id = id)
}