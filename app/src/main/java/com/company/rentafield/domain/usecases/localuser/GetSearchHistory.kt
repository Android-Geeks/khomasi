package com.company.rentafield.domain.usecases.localuser

import com.company.rentafield.data.repositories.localuser.LocalUserRepository
import kotlinx.coroutines.flow.Flow

class GetSearchHistory(
    private val localUserRepository: LocalUserRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return localUserRepository.getSearchHistory()
    }
}