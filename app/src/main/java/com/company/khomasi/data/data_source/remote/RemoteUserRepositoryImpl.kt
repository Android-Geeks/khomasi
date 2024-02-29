package com.company.khomasi.data.data_source.remote


import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserDetails
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.domain.model.VerificationResponse
import com.company.khomasi.domain.repository.RemoteUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteUserRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemoteUserRepository {
    override suspend fun registerUser(userDetails: UserDetails): UserRegisterResponse {
        return retrofitService.registerUser(userDetails)
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Flow<DataState<UserLoginResponse>> {
        return flow {
            emit(DataState.Loading)
            delay(5000)
            try {
                val response = retrofitService.loginUser(email, password)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getVerificationCode(email: String): VerificationResponse {
        return retrofitService.getVerificationCode(email)
    }

    override suspend fun confirmEmail(email: String, code: String): String {
        return retrofitService.confirmEmail(email, code)
    }

    override suspend fun recoverAccount(email: String, code: String, newPassword: String): String {
        return retrofitService.recoverAccount(email, code, newPassword)
    }
}