package com.company.rentafield


import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.company.rentafield.domain.use_case.ai.AiUseCases
import com.company.rentafield.workers.UploadVideoWorker
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}

class CustomWorkerFactory @Inject constructor(
    private val aiUseCases: AiUseCases
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = UploadVideoWorker(
        aiUseCases = aiUseCases,
        context = appContext,
        workerParams = workerParameters
    )
}