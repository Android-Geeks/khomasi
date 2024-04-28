package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemotePlaygroundRepository

class GetPlaygroundReviewsUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(token: String, id: Int) =
        remotePlaygroundRepository.getPlaygroundReviews(token = token, id = id)
}