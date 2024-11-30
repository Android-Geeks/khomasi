package com.company.rentafield.di


import com.company.rentafield.data.services.RetrofitAiService
import com.company.rentafield.data.services.RetrofitAuthService
import com.company.rentafield.data.services.RetrofitPlaygroundService
import com.company.rentafield.data.services.RetrofitUserService
import com.company.rentafield.utils.Constants.AI_URL
import com.company.rentafield.utils.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
            connectTimeout(20, TimeUnit.MINUTES) // connect timeout
            readTimeout(30, TimeUnit.MINUTES) // socket timeout
            writeTimeout(20, TimeUnit.MINUTES) // write timeout
        }.build()
    }


    private fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAiService(okHttpClient: OkHttpClient): RetrofitAiService {
        return provideRetrofit(okHttpClient, AI_URL)
            .create(RetrofitAiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitAuthService(okHttpClient: OkHttpClient): RetrofitAuthService {
        return provideRetrofit(okHttpClient, BASE_URL)
            .create(RetrofitAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitUserService(okHttpClient: OkHttpClient): RetrofitUserService {
        return provideRetrofit(okHttpClient, BASE_URL)
            .create(RetrofitUserService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitPlaygroundService(okHttpClient: OkHttpClient): RetrofitPlaygroundService {
        return provideRetrofit(okHttpClient, BASE_URL)
            .create(RetrofitPlaygroundService::class.java)
    }
}