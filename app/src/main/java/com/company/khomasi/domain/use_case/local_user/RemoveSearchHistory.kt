package com.company.khomasi.domain.use_case.local_user

import com.company.khomasi.domain.repository.LocalUserRepository

class RemoveSearchHistory(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke() = localUserRepository.removeSearchHistory()
}