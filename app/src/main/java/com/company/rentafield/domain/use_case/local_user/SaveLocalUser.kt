package com.company.rentafield.domain.use_case.local_user

import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.repository.LocalUserRepository

class SaveLocalUser(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(localUser: LocalUser) = localUserRepository.saveLocalUser(localUser)
}