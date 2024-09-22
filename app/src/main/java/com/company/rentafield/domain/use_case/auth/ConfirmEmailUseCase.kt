package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.repository.RemoteUserAuthorization

class ConfirmEmailUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(email: String, code: String) =
        remoteUserAuthorization.confirmEmail(email, code)
}