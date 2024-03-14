package com.company.khomasi.domain.use_case.auth

import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.repository.RemoteUserRepository

class RegisterUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(userRegisterData: UserRegisterData) =
        remoteUserRepository.registerUser(userRegisterData)
}