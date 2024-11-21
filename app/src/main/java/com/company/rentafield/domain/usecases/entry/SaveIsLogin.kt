package com.company.rentafield.domain.usecases.entry

import com.company.rentafield.data.repositories.localuser.LocalUserRepository

class SaveIsLogin(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(isLogin: Boolean) {
        localUserRepository.saveIsLogin(isLogin)
    }

}