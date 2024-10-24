package com.company.rentafield.data.data_source

import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.UserDataResponse
import com.company.rentafield.domain.model.ai.AiResponse
import com.company.rentafield.domain.model.auth.UserLoginResponse
import com.company.rentafield.domain.model.auth.UserRegisterData
import com.company.rentafield.domain.model.auth.UserRegisterResponse
import com.company.rentafield.domain.model.auth.VerificationResponse
import com.company.rentafield.domain.model.booking.BookingPlaygroundResponse
import com.company.rentafield.domain.model.booking.MyBookingsResponse
import com.company.rentafield.domain.model.booking.PlaygroundReviewRequest
import com.company.rentafield.domain.model.favourite.FavouritePlaygroundResponse
import com.company.rentafield.domain.model.playground.BookingRequest
import com.company.rentafield.domain.model.playground.FreeTimeSlotsResponse
import com.company.rentafield.domain.model.playground.PlaygroundReviewsResponse
import com.company.rentafield.domain.model.playground.PlaygroundScreenResponse
import com.company.rentafield.domain.model.playground.PlaygroundsResponse
import com.company.rentafield.domain.model.search.FilteredPlaygroundResponse
import com.company.rentafield.domain.model.user.FeedbackRequest
import com.company.rentafield.domain.model.user.ProfileImageResponse
import com.company.rentafield.domain.model.user.UserUpdateData
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

    @GET("User/playgrounds")
    suspend fun getPlaygrounds(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): Response<PlaygroundsResponse>

    @GET("Playground/playground")
    suspend fun getSpecificPlayground(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<PlaygroundScreenResponse>

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

    @GET("Playground/open-slots")
    suspend fun getOpenSlots(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
        @Query("dayDiff") dayDiff: Int
    ): Response<FreeTimeSlotsResponse>


    @GET("User/picture")
    suspend fun getProfileImage(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): Response<ProfileImageResponse>

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


    @GET("Review/playground-reviews")
    suspend fun getPlaygroundReviews(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<PlaygroundReviewsResponse>

    @GET("User/filtered-playgrounds")
    suspend fun getFilteredPlaygrounds(
        @Header("Authorization") token: String,
        @Query("id") id: String,
        @Query("price") price: Int,
        @Query("type") type: Int,
        @Query("bookingTime") bookingTime: String,
        @Query("duration") duration: Double
    ): Response<FilteredPlaygroundResponse>


    @POST("Playground/booking")
    suspend fun bookingPlayground(
        @Header("Authorization") token: String,
        @Body body: BookingRequest
    ): Response<BookingPlaygroundResponse>

    @GET("AI/ai-service")
    suspend fun getUploadVideoStatus(@Query("id") id: String): Response<MessageResponse>

    @GET("AI/ai-response")
    suspend fun getAiResults(@Query("id") id: String): Response<AiResponse>

    @GET("User/user-data")
    suspend fun userData(
        @Header("Authorization") token: String,
        @Query("id") userId: String,
    ): Response<UserDataResponse>
}