package com.company.rentafield.data.datasource

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


interface RetrofitService {
    @POST("Account/register/user")
    suspend fun registerUser(@Body user: com.company.rentafield.data.models.auth.UserRegisterData): Response<com.company.rentafield.data.models.auth.UserRegisterResponse>

    @POST("Account/login")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("passwd") password: String
    ): Response<com.company.rentafield.data.models.auth.UserLoginResponse>

    @GET("Account/confirmation-code")
    suspend fun getVerificationCode(@Query("email") email: String): Response<com.company.rentafield.data.models.auth.VerificationResponse>

    @PUT("Account/email-confirmation")
    suspend fun confirmEmail(
        @Query("email") email: String,
        @Query("code") code: String
    ): Response<com.company.rentafield.data.models.MessageResponse>

    @PUT("Account/account-recovery")
    suspend fun recoverAccount(
        @Query("email") email: String,
        @Query("code") code: String,
        @Query("newPassword") newPassword: String
    ): Response<com.company.rentafield.data.models.MessageResponse>

    @GET("User/playgrounds")
    suspend fun getPlaygrounds(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): Response<com.company.rentafield.data.models.playground.PlaygroundsResponse>

    @GET("Playground/playground")
    suspend fun getSpecificPlayground(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<com.company.rentafield.data.models.playground.PlaygroundScreenResponse>

    @GET("User/bookings")
    suspend fun getUserBookings(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): Response<com.company.rentafield.data.models.booking.MyBookingsResponse>

    @POST("User/favorite")
    suspend fun addUserFavouritePlayground(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Query("playgroundId") playgroundId: Int
    ): Response<com.company.rentafield.data.models.MessageResponse>

    @DELETE("User/favorite")
    suspend fun deleteUserFavouritePlayground(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Query("playgroundId") playgroundId: Int
    ): Response<com.company.rentafield.data.models.MessageResponse>

    @GET("User/favorite-playgrounds")
    suspend fun getUserFavouritePlaygrounds(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
    ): Response<com.company.rentafield.data.models.favourite.FavouritePlaygroundResponse>

    @Multipart
    @POST("User/picture")
    suspend fun uploadProfilePicture(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Part picture: MultipartBody.Part
    ): Response<com.company.rentafield.data.models.MessageResponse>


    @PUT("User/user")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Body user: com.company.rentafield.data.models.user.UserUpdateData
    ): Response<com.company.rentafield.data.models.MessageResponse>

    @POST("User/feedback")
    suspend fun sendFeedback(
        @Header("Authorization") token: String,
        @Body feedback: com.company.rentafield.data.models.user.FeedbackRequest
    ): Response<com.company.rentafield.data.models.MessageResponse>

    @GET("Playground/open-slots")
    suspend fun getOpenSlots(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
        @Query("dayDiff") dayDiff: Int
    ): Response<com.company.rentafield.data.models.playground.FreeTimeSlotsResponse>


    @GET("User/picture")
    suspend fun getProfileImage(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): Response<com.company.rentafield.data.models.user.ProfileImageResponse>

    @PUT("Playground/cancel-booking")
    suspend fun cancelBooking(
        @Header("Authorization") token: String,
        @Query("bookingID") bookingId: Int,
        @Query("isUser") isUser: Boolean
    ): Response<com.company.rentafield.data.models.MessageResponse>

    @POST("Review/review")
    suspend fun addPlaygroundReview(
        @Header("Authorization") token: String,
        @Body playgroundReview: com.company.rentafield.data.models.booking.PlaygroundReviewRequest
    ): Response<com.company.rentafield.data.models.MessageResponse>


    @GET("Review/playground-reviews")
    suspend fun getPlaygroundReviews(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<com.company.rentafield.data.models.playground.PlaygroundReviewsResponse>

    @GET("User/filtered-playgrounds")
    suspend fun getFilteredPlaygrounds(
        @Header("Authorization") token: String,
        @Query("id") id: String,
        @Query("price") price: Int,
        @Query("type") type: Int,
        @Query("bookingTime") bookingTime: String,
        @Query("duration") duration: Double
    ): Response<com.company.rentafield.data.models.search.FilteredPlaygroundResponse>


    @POST("Playground/booking")
    suspend fun bookingPlayground(
        @Header("Authorization") token: String,
        @Body body: com.company.rentafield.data.models.playground.BookingRequest
    ): Response<com.company.rentafield.data.models.booking.BookingPlaygroundResponse>

    @GET("AI/ai-service")
    suspend fun getUploadVideoStatus(@Query("id") id: String): Response<com.company.rentafield.data.models.MessageResponse>

    @GET("AI/ai-response")
    suspend fun getAiResults(@Query("id") id: String): Response<com.company.rentafield.data.models.ai.AiResponse>

    @GET("User/user-data")
    suspend fun userData(
        @Header("Authorization") token: String,
        @Query("id") userId: String,
    ): Response<com.company.rentafield.data.models.UserDataResponse>
}