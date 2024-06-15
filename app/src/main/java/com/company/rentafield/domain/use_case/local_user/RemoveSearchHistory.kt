package com.company.rentafield.domain.use_case.local_user

import com.company.rentafield.domain.repository.LocalUserRepository

class RemoveSearchHistory(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke() = localUserRepository.removeSearchHistory()
}