package com.company.khomasi.domain.use_case.app_entry

import com.company.khomasi.domain.repository.LocalUserRepository
import com.company.khomasi.navigation.Screens
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserRepository: LocalUserRepository
) {

    operator fun invoke(): Flow<Screens> {
        return localUserRepository.readAppEntry()
    }

}