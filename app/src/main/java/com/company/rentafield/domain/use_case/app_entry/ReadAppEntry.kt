package com.company.rentafield.domain.use_case.app_entry

import com.company.rentafield.domain.repository.LocalUserRepository
import com.company.rentafield.navigation.Screens
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserRepository: LocalUserRepository
) {

    operator fun invoke(): Flow<Screens> {
        return localUserRepository.readAppEntry()
    }

}