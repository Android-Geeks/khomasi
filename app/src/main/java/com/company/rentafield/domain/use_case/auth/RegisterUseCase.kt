package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.model.auth.UserRegisterData
import com.company.rentafield.domain.repository.RemoteUserAuthorization

class RegisterUseCase(
    private val remoteUserAuthorization: RemoteUserAuthorization
) {
    suspend operator fun invoke(userRegisterData: UserRegisterData) =
        remoteUserAuthorization.registerUser(userRegisterData)
}