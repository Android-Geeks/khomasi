package com.company.rentafield.data.services

import com.company.rentafield.domain.models.MessageResponse
import com.company.rentafield.domain.models.auth.UserLoginResponse
import com.company.rentafield.domain.models.auth.UserRegisterData
import com.company.rentafield.domain.models.auth.UserRegisterResponse
import com.company.rentafield.domain.models.auth.VerificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface RetrofitAuthService {
    @POST("Account/register/user")
    suspend fun registerUser(@Body user: UserRegisterData): Response<UserRegisterResponse>

    @POST("Account/login")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("passwd") password: String
    ): Response<UserLoginResponse>

    @GET("Account/confirmation-code")
    suspend fun getVerificationCode(@Query("email") email: String): Response<VerificationResponse>

    @PUT("Account/email-confirmation")
    suspend fun confirmEmail(
        @Query("email") email: String,
        @Query("code") code: String
    ): Response<MessageResponse>

    @PUT("Account/account-recovery")
    suspend fun recoverAccount(
        @Query("email") email: String,
        @Query("code") code: String,
        @Query("newPassword") newPassword: String
    ): Response<MessageResponse>
}