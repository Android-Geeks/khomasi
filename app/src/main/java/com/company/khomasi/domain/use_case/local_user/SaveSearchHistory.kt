package com.company.khomasi.domain.use_case.local_user

import com.company.khomasi.domain.repository.LocalUserRepository

class SaveSearchHistory(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(searchQuery: String) {
        localUserRepository.saveSearchHistory(searchQuery)
    }
}