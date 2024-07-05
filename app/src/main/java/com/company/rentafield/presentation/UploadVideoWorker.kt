package com.company.rentafield.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.use_case.ai.AiUseCases
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
    private val aiUseCases: AiUseCases
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val id = inputData.getString("id") ?: return Result.failure()
        val videoFilePath = inputData.getString("videoFilePath") ?: return Result.failure()

        val idRequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())

        val videoFileUri = Uri.parse(videoFilePath)
        val videoFile = createFileFromUri(videoFileUri) ?: return Result.failure()
        val videoRequestBody = videoFile.asRequestBody("video/*".toMediaTypeOrNull())
        val videoPart = MultipartBody.Part.createFormData("video", videoFile.name, videoRequestBody)

        return try {
            var resultData: DataState<MessageResponse>? = null
            aiUseCases.uploadVideoUseCase(idRequestBody, videoPart).collect { result ->
                resultData = result
            }
            Log.d("UploadVideoWorker", "doWork: $resultData")
            if (resultData != null) {
                Result.success(workDataOf("result" to resultData.toString()))
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Log.d("UploadVideoWorker", "doWork: ${e.message}")
            Result.failure()
        }
    }

    private fun createFileFromUri(uri: Uri): File? {
        val contentResolver = applicationContext.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val outputFile = File(applicationContext.cacheDir, "upload.mp4")
        outputFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return outputFile
    }
}

