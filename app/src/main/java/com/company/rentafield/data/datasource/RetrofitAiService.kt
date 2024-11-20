package com.company.rentafield.data.datasource

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitAiService {

    @Multipart
    @POST("upload")
    suspend fun uploadVideo(
        @Part("id") id: RequestBody,
        @Part video: MultipartBody.Part
    ): Response<com.company.rentafield.data.models.MessageResponse>
}