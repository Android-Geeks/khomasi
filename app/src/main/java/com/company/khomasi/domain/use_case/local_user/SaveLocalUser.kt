package com.company.khomasi.domain.use_case.local_user

import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.repository.LocalUserRepository

class SaveLocalUser(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(localUser: LocalUser) = localUserRepository.saveLocalUser(localUser)
}