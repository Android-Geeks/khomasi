package com.company.rentafield.data.services

import com.company.rentafield.domain.models.MessageResponse
import com.company.rentafield.domain.models.UserDataResponse
import com.company.rentafield.domain.models.ai.AiResponse
import com.company.rentafield.domain.models.booking.MyBookingsResponse
import com.company.rentafield.domain.models.booking.PlaygroundReviewRequest
import com.company.rentafield.domain.models.favourite.FavouritePlaygroundResponse
import com.company.rentafield.domain.models.user.FeedbackRequest
import com.company.rentafield.domain.models.user.ProfileImageResponse
import com.company.rentafield.domain.models.user.UserUpdateData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query


interface RetrofitUserService {

    @GET("User/bookings")
    suspend fun getUserBookings(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): Response<MyBookingsResponse>

    @POST("User/favorite")
    suspend fun addUserFavouritePlayground(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Query("playgroundId") playgroundId: Int
    ): Response<MessageResponse>

    @DELETE("User/favorite")
    suspend fun deleteUserFavouritePlayground(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Query("playgroundId") playgroundId: Int
    ): Response<MessageResponse>

    @GET("User/favorite-playgrounds")
    suspend fun getUserFavouritePlaygrounds(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
    ): Response<FavouritePlaygroundResponse>

    @Multipart
    @POST("User/picture")
    suspend fun uploadProfilePicture(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Part picture: MultipartBody.Part
    ): Response<MessageResponse>

    @GET("User/picture")
    suspend fun getProfileImage(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): Response<ProfileImageResponse>

    @PUT("User/user")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Body user: UserUpdateData
    ): Response<MessageResponse>

    @POST("User/feedback")
    suspend fun sendFeedback(
        @Header("Authorization") token: String,
        @Body feedback: FeedbackRequest
    ): Response<MessageResponse>

    @GET("User/user-data")
    suspend fun userData(
        @Header("Authorization") token: String,
        @Query("id") userId: String,
    ): Response<UserDataResponse>

    @GET("AI/ai-service")
    suspend fun getUploadVideoStatus(@Query("id") id: String): Response<MessageResponse>

    @GET("AI/ai-response")
    suspend fun getAiResults(@Query("id") id: String): Response<AiResponse>

    @PUT("Playground/cancel-booking")
    suspend fun cancelBooking(
        @Header("Authorization") token: String,
        @Query("bookingID") bookingId: Int,
        @Query("isUser") isUser: Boolean
    ): Response<MessageResponse>

    @POST("Review/review")
    suspend fun addPlaygroundReview(
        @Header("Authorization") token: String,
        @Body playgroundReview: PlaygroundReviewRequest
    ): Response<MessageResponse>
}