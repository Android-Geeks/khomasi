package com.company.khomasi.data.data_source.remote

import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.MessageResponse
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.domain.model.UserUpdateData
import com.company.khomasi.domain.model.VerificationResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @GET("User/bookings")
    suspend fun getUserBookings(
        @Header("Authorization") token: String,
        @Query("id") id:String
    ): MyBookingsResponse

    @POST("User/favorite")
    suspend fun userFavourite(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Query("playgroundId") playgroundId: String

    ):MessageResponse

    @DELETE("User/favorite")
    suspend fun deleteUserFavourite(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Query("playgroundId") playgroundId: String
    ): MessageResponse

    @GET("User/favorite-playgrounds")
    suspend fun  getUserFavouritePlaygrounds(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
    ): FavouritePlaygroundResponse

    @POST("User/picture")
    suspend fun uploadProfilePicture(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Body picture: String
    ): MessageResponse

    @PUT("User/user")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Body user: UserUpdateData
    ): MessageResponse
}