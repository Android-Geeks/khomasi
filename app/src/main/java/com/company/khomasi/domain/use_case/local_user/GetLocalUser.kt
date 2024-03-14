package com.company.khomasi.domain.use_case.local_user

import com.company.khomasi.domain.repository.LocalUserRepository

class GetLocalUser(
    private val localUserRepository: LocalUserRepository
) {
    operator fun invoke() = localUserRepository.getLocalUser()
}