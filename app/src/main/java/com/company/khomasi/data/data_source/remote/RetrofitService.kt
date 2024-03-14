package com.company.khomasi.data.data_source.remote

import com.company.khomasi.domain.model.MessageResponse
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.domain.model.VerificationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface RetrofitService {
    @POST("Account/UserRegister")
    suspend fun registerUser(@Body user: UserRegisterData): UserRegisterResponse

    @POST("Account/Login")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("passwd") password: String
    ): UserLoginResponse

    @GET("Account/GetConfirmationCode")
    suspend fun getVerificationCode(@Query("email") email: String): VerificationResponse

    @PUT("Account/ConfirmEmail")
    suspend fun confirmEmail(
        @Query("email") email: String,
        @Query("code") code: String
    ): MessageResponse

    @PUT("Account/RecoverAccount")
    suspend fun recoverAccount(
        @Query("email") email: String,
        @Query("code") code: String,
        @Query("newPassword") newPassword: String
    ): MessageResponse
}