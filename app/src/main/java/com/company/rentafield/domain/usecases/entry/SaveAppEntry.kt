package com.company.rentafield.domain.usecases.entry

import com.company.rentafield.data.repositories.localuser.LocalUserRepository

class SaveAppEntry(
    private val localUserRepository: LocalUserRepository
) {

    suspend operator fun invoke() {
        localUserRepository.saveAppEntry()
    }

}