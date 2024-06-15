package com.company.rentafield.domain.use_case.app_entry

import com.company.rentafield.domain.repository.LocalUserRepository

class SaveAppEntry(
    private val localUserRepository: LocalUserRepository
) {

    suspend operator fun invoke(){
        localUserRepository.saveAppEntry()
    }

}