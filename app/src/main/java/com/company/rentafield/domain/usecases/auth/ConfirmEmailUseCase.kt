package com.company.rentafield.domain.usecases.auth

import com.company.rentafield.data.repositories.auth.RemoteUserAuthorization

class ConfirmEmailUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(email: String, code: String) =
        remoteUserAuthorization.confirmEmail(email, code)
}