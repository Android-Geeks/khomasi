package com.company.rentafield.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.usecases.ai.AiUseCases
import com.company.rentafield.presentation.mainactivity.MainActivity
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

    companion object {
        private const val CHANNEL_ID = "UploadVideoWorkerChannel"
        private const val NOTIFICATION_ID = 1
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        createNotificationChannel()
        val notification = createNotification()
        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    override suspend fun doWork(): Result {
        val id = inputData.getString("id") ?: return Result.failure()
        val videoFilePath = inputData.getString("videoFilePath") ?: return Result.failure()

        val idRequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())

        val videoFileUri = Uri.parse(videoFilePath)
        val videoFile = createFileFromUri(videoFileUri) ?: return Result.failure()
        val videoRequestBody = videoFile.asRequestBody("video/*".toMediaTypeOrNull())
        val videoPart = MultipartBody.Part.createFormData("video", videoFile.name, videoRequestBody)


        return try {
            var resultData: DataState<com.company.rentafield.domain.models.MessageResponse>? = null
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.channel_name)
            val descriptionText = applicationContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.light_mode_splash)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(applicationContext.getString(R.string.notification_message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setProgress(100, 0, true)
            .setContentIntent(pendingIntent)
            .build()
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

