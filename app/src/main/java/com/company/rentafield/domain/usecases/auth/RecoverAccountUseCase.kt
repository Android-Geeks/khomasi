package com.company.rentafield.domain.usecases.auth

import com.company.rentafield.data.repositories.auth.RemoteUserAuthorization

class RecoverAccountUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(email: String, code: String, newPassword: String) =
        remoteUserAuthorization.recoverAccount(email, code, newPassword)
}