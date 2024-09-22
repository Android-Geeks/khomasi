package com.company.rentafield.domain.repository

import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.auth.UserLoginResponse
import com.company.rentafield.domain.model.auth.UserRegisterData
import com.company.rentafield.domain.model.auth.UserRegisterResponse
import com.company.rentafield.domain.model.auth.VerificationResponse
import kotlinx.coroutines.flow.Flow

interface RemoteUserAuthorization {
    suspend fun registerUser(userRegisterData: UserRegisterData): Flow<DataState<UserRegisterResponse>>
    suspend fun loginUser(email: String, password: String): Flow<DataState<UserLoginResponse>>
    suspend fun getVerificationCode(email: String): Flow<DataState<VerificationResponse>>
    suspend fun confirmEmail(email: String, code: String): Flow<DataState<MessageResponse>>
    suspend fun recoverAccount(
        email: String,
        code: String,
        newPassword: String
    ): Flow<DataState<MessageResponse>>
}