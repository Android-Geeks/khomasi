package com.company.khomasi.data.repository


import com.company.khomasi.data.data_source.remote.RetrofitService
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.MessageResponse
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.model.UserBookingsResponse
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.domain.model.VerificationResponse
import com.company.khomasi.domain.repository.RemoteUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteUserRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemoteUserRepository {
    override suspend fun registerUser(userRegisterData: UserRegisterData): Flow<DataState<UserRegisterResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.registerUser(userRegisterData)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Flow<DataState<UserLoginResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.loginUser(email, password)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getVerificationCode(email: String): Flow<DataState<VerificationResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.getVerificationCode(email)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun confirmEmail(
        email: String,
        code: String
    ): Flow<DataState<MessageResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.confirmEmail(email, code)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun recoverAccount(
        email: String,
        code: String,
        newPassword: String
    ): Flow<DataState<MessageResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.recoverAccount(email, code, newPassword)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPlaygrounds(
        token: String,
        userId: String
    ): Flow<DataState<PlaygroundsResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.getPlaygrounds(token, userId)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSpecificPlayground(
        token: String,
        id: Int
    ): Flow<DataState<PlaygroundScreenResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.getSpecificPlayground(token, id)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserBookings(id: String): Flow<DataState<UserBookingsResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.getUserBookings(id)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteUserFavourite(
        userId: String,
        playgroundId: String
    ): Flow<DataState<MessageResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.deleteUserFavourite(userId, playgroundId)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun getUserFavouritePlaygrounds(
        userId: String,
    ): Flow<DataState<FavouritePlaygroundResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.getUserFavouritePlaygrounds(userId)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun userFavourite(
        userId: String,
        playgroundId: String
    ): Flow<DataState<MessageResponse>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = retrofitService.userFavourite(userId, playgroundId)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}