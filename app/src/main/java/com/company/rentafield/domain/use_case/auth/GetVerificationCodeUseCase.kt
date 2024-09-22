package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.repository.RemoteUserAuthorization

class GetVerificationCodeUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(email: String) =
        remoteUserAuthorization.getVerificationCode(email)
}