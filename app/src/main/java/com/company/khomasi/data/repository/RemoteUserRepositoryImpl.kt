package com.company.khomasi.data.repository


import com.company.khomasi.data.data_source.remote.RetrofitService
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FeedbackRequest
import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.model.UserUpdateData
import com.company.khomasi.domain.repository.RemoteUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response

class RemoteUserRepositoryImpl(
    private val retrofitService: RetrofitService
) : RemoteUserRepository {
    override suspend fun registerUser(userRegisterData: UserRegisterData) =
        handleApi { retrofitService.registerUser(userRegisterData) }

    override suspend fun loginUser(email: String, password: String) =
        handleApi { retrofitService.loginUser(email, password) }

    override suspend fun getVerificationCode(email: String) =
        handleApi { retrofitService.getVerificationCode(email) }

    override suspend fun confirmEmail(email: String, code: String) =
        handleApi { retrofitService.confirmEmail(email, code) }

    override suspend fun recoverAccount(email: String, code: String, newPassword: String) =
        handleApi { retrofitService.recoverAccount(email, code, newPassword) }

    override suspend fun getPlaygrounds(token: String, userId: String) =
        handleApi { retrofitService.getPlaygrounds(token, userId) }

    override suspend fun getSpecificPlayground(token: String, id: Int) =
        handleApi { retrofitService.getSpecificPlayground(token, id) }

    override suspend fun getUserBookings(token: String, id: String) =
        handleApi { retrofitService.getUserBookings(token, id) }

    override suspend fun deleteUserFavourite(token: String, userId: String, playgroundId: String) =
        handleApi { retrofitService.deleteUserFavourite(token, userId, playgroundId) }

    override suspend fun getUserFavouritePlaygrounds(token: String, userId: String) =
        handleApi { retrofitService.getUserFavouritePlaygrounds(token, userId) }

    override suspend fun userFavourite(token: String, userId: String, playgroundId: String) =
        handleApi { retrofitService.userFavourite(token, userId, playgroundId) }

    override suspend fun uploadProfilePicture(token: String, userId: String, picture: String) =
        handleApi { retrofitService.uploadProfilePicture(token, userId, picture) }

    override suspend fun updateUser(token: String, userId: String, user: UserUpdateData) =
        handleApi { retrofitService.updateUser(token, userId, user) }

    override suspend fun sendFeedback(token: String, feedback: FeedbackRequest) =
        handleApi { retrofitService.sendFeedback(token, feedback) }


}

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): Flow<DataState<T>> {
    return flow {
        emit(DataState.Loading)
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(DataState.Success(body))
            } else {
                emit(DataState.Error(response.code(), response.message()))
            }
        } catch (e: HttpException) {
            emit(DataState.Error(e.code(), e.message()))
        } catch (e: Throwable) {
            emit(DataState.Error(0, e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)
}