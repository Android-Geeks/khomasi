package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemotePlaygroundRepository

class GetPlaygroundReviewsUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(token: String, id: Int) =
        remotePlaygroundRepository.getPlaygroundReviews(token = token, id = id)
}