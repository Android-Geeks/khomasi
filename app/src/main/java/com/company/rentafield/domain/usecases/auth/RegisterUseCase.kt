package com.company.rentafield.domain.usecases.auth

import com.company.rentafield.data.repositories.auth.RemoteUserAuthorization

class RegisterUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(userRegisterData: com.company.rentafield.data.models.auth.UserRegisterData) =
        remoteUserAuthorization.registerUser(userRegisterData)
}