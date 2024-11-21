package com.company.rentafield.domain.usecases.localuser

import com.company.rentafield.data.repositories.localuser.LocalUserRepository

class SaveSearchHistory(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(searchQuery: String) {
        localUserRepository.saveSearchHistory(searchQuery)
    }
}