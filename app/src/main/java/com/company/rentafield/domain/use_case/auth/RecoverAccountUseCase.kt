package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.repository.RemoteUserAuthorization

class RecoverAccountUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(email: String, code: String, newPassword: String) =
        remoteUserAuthorization.recoverAccount(email, code, newPassword)
}