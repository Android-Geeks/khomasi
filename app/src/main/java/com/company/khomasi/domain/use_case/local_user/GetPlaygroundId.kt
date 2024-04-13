package com.company.khomasi.domain.use_case.local_user

import com.company.khomasi.domain.repository.LocalUserRepository

class GetPlaygroundId(
    private val localUserRepository: LocalUserRepository
) {
    operator fun invoke() = localUserRepository.getPlaygroundId()
}