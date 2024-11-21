package com.company.rentafield.domain.usecases.auth

import com.company.rentafield.data.repositories.auth.RemoteUserAuthorization

class LoginUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(email: String, password: String) =
        remoteUserAuthorization.loginUser(email, password)

}