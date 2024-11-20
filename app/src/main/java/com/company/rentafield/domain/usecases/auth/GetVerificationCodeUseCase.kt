package com.company.rentafield.domain.usecases.auth

import com.company.rentafield.data.repositories.auth.RemoteUserAuthorization

class GetVerificationCodeUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(email: String) =
        remoteUserAuthorization.getVerificationCode(email)
}