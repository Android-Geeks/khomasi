package com.company.rentafield.domain.usecases.entry

import com.company.rentafield.data.repositories.localuser.LocalUserRepository
import com.company.rentafield.presentation.navigation.components.Screens
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserRepository: LocalUserRepository
) {

    operator fun invoke(): Flow<Screens> {
        return localUserRepository.readAppEntry()
    }

}