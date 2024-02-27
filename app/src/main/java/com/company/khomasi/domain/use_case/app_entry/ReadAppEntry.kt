package com.company.khomasi.domain.use_case.app_entry

import com.company.khomasi.domain.repository.LocalUserRepository
import com.company.khomasi.navigation.Routes
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserRepository: LocalUserRepository
) {

    operator fun invoke(): Flow<Routes> {
        return localUserRepository.readAppEntry()
    }

}