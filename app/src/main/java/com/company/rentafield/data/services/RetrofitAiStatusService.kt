package com.company.rentafield.data.services

import com.company.rentafield.domain.models.MessageResponse
import com.company.rentafield.domain.models.ai.AiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAiStatusService {
    @GET("AI/ai-service")
    suspend fun getUploadVideoStatus(@Query("id") id: String): Response<MessageResponse>

    @GET("AI/ai-response")
    suspend fun getAiResults(@Query("id") id: String): Response<AiResponse>
}