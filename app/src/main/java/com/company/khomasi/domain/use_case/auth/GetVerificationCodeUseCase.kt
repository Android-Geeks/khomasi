package com.company.khomasi.domain.use_case.auth

import com.company.khomasi.domain.repository.RemoteUserRepository

class GetVerificationCodeUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(email: String) =
        remoteUserRepository.getVerificationCode(email)
}