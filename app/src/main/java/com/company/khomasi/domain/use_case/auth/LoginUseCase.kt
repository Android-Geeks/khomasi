package com.company.khomasi.domain.use_case.auth

import com.company.khomasi.domain.repository.RemoteUserRepository

class LoginUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        remoteUserRepository.loginUser(email, password)

}