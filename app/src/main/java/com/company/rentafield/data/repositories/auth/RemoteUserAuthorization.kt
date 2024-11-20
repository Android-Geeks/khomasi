package com.company.rentafield.data.repositories.auth

import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.Flow

interface RemoteUserAuthorization {
    suspend fun registerUser(userRegisterData: com.company.rentafield.data.models.auth.UserRegisterData): Flow<DataState<com.company.rentafield.data.models.auth.UserRegisterResponse>>
    suspend fun loginUser(
        email: String,
        password: String
    ): Flow<DataState<com.company.rentafield.data.models.auth.UserLoginResponse>>

    suspend fun getVerificationCode(email: String): Flow<DataState<com.company.rentafield.data.models.auth.VerificationResponse>>
    suspend fun confirmEmail(
        email: String,
        code: String
    ): Flow<DataState<com.company.rentafield.data.models.MessageResponse>>

    suspend fun recoverAccount(
        email: String,
        code: String,
        newPassword: String
    ): Flow<DataState<com.company.rentafield.data.models.MessageResponse>>
}