package com.company.rentafield.domain.usecases.localuser

import com.company.rentafield.data.repositories.localuser.LocalUserRepository

class SaveLocalUser(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(localUser: com.company.rentafield.data.models.LocalUser) =
        localUserRepository.saveLocalUser(localUser)
}