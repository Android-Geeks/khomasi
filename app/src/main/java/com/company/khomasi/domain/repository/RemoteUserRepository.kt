package com.company.khomasi.domain.repository

import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserDetails
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.domain.model.VerificationResponse
import kotlinx.coroutines.flow.Flow

interface RemoteUserRepository {
    suspend fun registerUser(userDetails: UserDetails): UserRegisterResponse
    suspend fun loginUser(email: String, password: String): Flow<DataState<UserLoginResponse>>
    suspend fun getVerificationCode(email: String): VerificationResponse
    suspend fun confirmEmail(email: String, code: String): String
    suspend fun recoverAccount(email: String, code: String, newPassword: String): String

}