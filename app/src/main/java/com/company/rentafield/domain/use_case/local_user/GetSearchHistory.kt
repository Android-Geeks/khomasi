package com.company.rentafield.domain.use_case.local_user

import com.company.rentafield.domain.repository.LocalUserRepository
import kotlinx.coroutines.flow.Flow

class GetSearchHistory(
    private val localUserRepository: LocalUserRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return localUserRepository.getSearchHistory()
    }
}