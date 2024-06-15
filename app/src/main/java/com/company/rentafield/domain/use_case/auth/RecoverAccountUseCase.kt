package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.repository.RemoteUserRepository

class RecoverAccountUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(email: String, code: String, newPassword: String) =
        remoteUserRepository.recoverAccount(email, code, newPassword)
}