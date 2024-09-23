package com.company.rentafield.data.data_source

import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.ai.AiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RetrofitAiService {
    @GET("AI/ai-response")
    suspend fun getAiResults(@Query("id") id: String): Response<AiResponse>

    @Multipart
    @POST("upload")
    suspend fun uploadVideo(
        @Part("id") id: RequestBody,
        @Part video: MultipartBody.Part
    ): Response<MessageResponse>
}