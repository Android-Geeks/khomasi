package com.company.rentafield.domain.use_case.auth

import com.company.rentafield.domain.model.auth.UserRegisterData
import com.company.rentafield.domain.repository.RemoteUserRepository

class RegisterUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(userRegisterData: UserRegisterData) =
        remoteUserRepository.registerUser(userRegisterData)
}