package com.company.khomasi.domain.use_case.auth

import com.company.khomasi.domain.model.UserDetails
import com.company.khomasi.domain.repository.RemoteUserRepository

class RegisterUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(userDetails: UserDetails) =
        remoteUserRepository.registerUser(userDetails)
}