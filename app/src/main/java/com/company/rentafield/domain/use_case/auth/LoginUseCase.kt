package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.repository.RemoteUserAuthorization

class LoginUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(email: String, password: String) =
        remoteUserAuthorization.loginUser(email, password)

}