package com.company.khomasi.domain.use_case.app_entry

import com.company.khomasi.domain.repository.LocalUserRepository

class SaveIsLogin(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(isLogin: Boolean) {
        localUserRepository.saveIsLogin(isLogin)
    }

}