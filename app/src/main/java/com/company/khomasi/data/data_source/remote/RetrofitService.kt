package com.company.khomasi.data.data_source.remote

import com.company.khomasi.domain.model.MessageResponse
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.domain.model.VerificationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface RetrofitService {
    @POST("Account/register/user")
    suspend fun registerUser(@Body user: UserRegisterData): UserRegisterResponse

    @POST("Account/login")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("passwd") password: String
    ): UserLoginResponse

    @GET("Account/confirmation-code")
    suspend fun getVerificationCode(@Query("email") email: String): VerificationResponse

    @PUT("Account/email-confirmation")
    suspend fun confirmEmail(
        @Query("email") email: String,
        @Query("code") code: String
    ): MessageResponse

    @PUT("Account/account-recovery")
    suspend fun recoverAccount(
        @Query("email") email: String,
        @Query("code") code: String,
        @Query("newPassword") newPassword: String
    ): MessageResponse

    @GET("User/playgrounds")
    suspend fun getPlaygrounds(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): PlaygroundsResponse

    @GET("Playground/playground")
    suspend fun getSpecificPlayground(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): PlaygroundScreenResponse

}