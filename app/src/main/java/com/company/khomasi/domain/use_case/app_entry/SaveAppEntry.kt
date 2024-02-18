package com.company.khomasi.domain.use_case.app_entry

import com.company.khomasi.domain.repository.LocalUserRepository

class SaveAppEntry(
    private val localUserRepository: LocalUserRepository
) {

    suspend operator fun invoke(){
        localUserRepository.saveAppEntry()
    }

}