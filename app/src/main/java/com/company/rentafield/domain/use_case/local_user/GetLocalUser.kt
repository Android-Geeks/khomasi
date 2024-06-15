package com.company.rentafield.domain.use_case.local_user

import com.company.rentafield.domain.repository.LocalUserRepository

class GetLocalUser(
    private val localUserRepository: LocalUserRepository
) {
    operator fun invoke() = localUserRepository.getLocalUser()
}