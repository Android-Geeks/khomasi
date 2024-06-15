package com.company.rentafield.domain.use_case.local_user

import com.company.rentafield.domain.repository.LocalUserRepository

class SavePlaygroundId(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(playgroundId: Int) = localUserRepository.savePlaygroundId(playgroundId)
}