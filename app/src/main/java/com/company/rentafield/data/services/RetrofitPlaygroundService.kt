package com.company.rentafield.data.services

import com.company.rentafield.domain.models.booking.BookingPlaygroundResponse
import com.company.rentafield.domain.models.playground.BookingRequest
import com.company.rentafield.domain.models.playground.FreeTimeSlotsResponse
import com.company.rentafield.domain.models.playground.PlaygroundReviewsResponse
import com.company.rentafield.domain.models.playground.PlaygroundScreenResponse
import com.company.rentafield.domain.models.playground.PlaygroundsResponse
import com.company.rentafield.domain.models.search.FilteredPlaygroundResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitPlaygroundService {
    @GET("Playground/playground")
    suspend fun getSpecificPlayground(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<PlaygroundScreenResponse>

    @GET("User/playgrounds")
    suspend fun getPlaygrounds(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): Response<PlaygroundsResponse>

    @GET("User/filtered-playgrounds")
    suspend fun getFilteredPlaygrounds(
        @Header("Authorization") token: String,
        @Query("id") id: String,
        @Query("price") price: Int,
        @Query("type") type: Int,
        @Query("bookingTime") bookingTime: String,
        @Query("duration") duration: Double
    ): Response<FilteredPlaygroundResponse>

    @GET("Playground/open-slots")
    suspend fun getOpenSlots(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
        @Query("dayDiff") dayDiff: Int
    ): Response<FreeTimeSlotsResponse>

    @POST("Playground/booking")
    suspend fun bookingPlayground(
        @Header("Authorization") token: String,
        @Body body: BookingRequest
    ): Response<BookingPlaygroundResponse>

    @GET("Review/playground-reviews")
    suspend fun getPlaygroundReviews(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<PlaygroundReviewsResponse>
}