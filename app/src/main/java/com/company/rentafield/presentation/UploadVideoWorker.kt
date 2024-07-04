package com.company.rentafield.presentation

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.use_case.ai.AiUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@HiltWorker
class UploadVideoWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val aiUseCase: AiUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val id = inputData.getString("id") ?: return Result.failure()
        val videoFilePath = inputData.getString("videoFilePath") ?: return Result.failure()

        val idRequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())

        val videoFile = File(videoFilePath)
        val videoRequestBody = videoFile.asRequestBody("video/*".toMediaTypeOrNull())
        val videoPart = MultipartBody.Part.createFormData("video", videoFile.name, videoRequestBody)

        return try {
            var resultData: DataState<MessageResponse>? = null
            aiUseCase(idRequestBody, videoPart).collect { result ->
                resultData = result
            }
            if (resultData != null) {
                Result.success(workDataOf("result" to resultData))
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}