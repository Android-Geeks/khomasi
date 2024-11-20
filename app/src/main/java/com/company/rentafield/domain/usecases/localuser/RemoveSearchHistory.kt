package com.company.rentafield.domain.usecases.localuser

import com.company.rentafield.data.repositories.localuser.LocalUserRepository

class RemoveSearchHistory(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke() = localUserRepository.removeSearchHistory()
}