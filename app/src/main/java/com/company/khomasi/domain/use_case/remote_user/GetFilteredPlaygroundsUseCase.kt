package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemotePlaygroundRepository
import org.threeten.bp.LocalDateTime

class GetFilteredPlaygroundsUseCase(
    private val remotePlaygroundRepository: RemotePlaygroundRepository
) {
    suspend operator fun invoke(
        token: String,
        id: String,
        price: Int,
        bookingTime: LocalDateTime,
        duration: Double
    ) = remotePlaygroundRepository.getFilteredPlaygrounds(
        token,
        id,
        price,
        bookingTime,
        duration
    )
}