package com.company.rentafield.domain.use_case.local_user

import com.company.rentafield.domain.repository.LocalUserRepository

class GetPlaygroundId(
    private val localUserRepository: LocalUserRepository
) {
    operator fun invoke() = localUserRepository.getPlaygroundId()
}