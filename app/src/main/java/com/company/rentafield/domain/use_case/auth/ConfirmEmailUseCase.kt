package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.repository.RemoteUserRepository

class ConfirmEmailUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(email: String, code: String) =
        remoteUserRepository.confirmEmail(email, code)
}