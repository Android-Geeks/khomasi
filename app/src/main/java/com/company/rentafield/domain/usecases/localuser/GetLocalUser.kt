package com.company.rentafield.domain.usecases.localuser

import com.company.rentafield.data.repositories.localuser.LocalUserRepository

class GetLocalUser(
    private val localUserRepository: LocalUserRepository
) {
    operator fun invoke() = localUserRepository.getLocalUser()
}