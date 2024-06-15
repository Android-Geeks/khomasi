package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.repository.RemoteUserRepository

class GetVerificationCodeUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(email: String) =
        remoteUserRepository.getVerificationCode(email)
}