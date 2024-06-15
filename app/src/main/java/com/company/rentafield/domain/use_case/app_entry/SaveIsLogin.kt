package com.company.rentafield.domain.use_case.app_entry

import com.company.rentafield.domain.repository.LocalUserRepository

class SaveIsLogin(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(isLogin: Boolean) {
        localUserRepository.saveIsLogin(isLogin)
    }

}