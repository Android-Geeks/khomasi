package com.company.khomasi.di


import com.company.khomasi.data.data_source.remote.RetrofitService
import com.company.khomasi.data.repository.RemotePlaygroundRepositoryImpl
import com.company.khomasi.data.repository.RemoteUserRepositoryImpl
import com.company.khomasi.domain.repository.RemotePlaygroundRepository
import com.company.khomasi.domain.repository.RemoteUserRepository
import com.company.khomasi.domain.use_case.auth.AuthUseCases
import com.company.khomasi.domain.use_case.auth.ConfirmEmailUseCase
import com.company.khomasi.domain.use_case.auth.GetVerificationCodeUseCase
import com.company.khomasi.domain.use_case.auth.LoginUseCase
import com.company.khomasi.domain.use_case.auth.RecoverAccountUseCase
import com.company.khomasi.domain.use_case.auth.RegisterUseCase
import com.company.khomasi.domain.use_case.remote_user.DeleteUserFavouriteUseCase
import com.company.khomasi.domain.use_case.remote_user.GetFreeTimeSlotsUseCase
import com.company.khomasi.domain.use_case.remote_user.GetPlaygroundsUseCase
import com.company.khomasi.domain.use_case.remote_user.GetSpecificPlaygroundUseCase
import com.company.khomasi.domain.use_case.remote_user.GetUserBookingsUseCase
import com.company.khomasi.domain.use_case.remote_user.GetUserFavoritePlaygroundsUseCase
import com.company.khomasi.domain.use_case.remote_user.RemotePlaygroundUseCase
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import com.company.khomasi.domain.use_case.remote_user.SendFeedbackUseCase
import com.company.khomasi.domain.use_case.remote_user.UpdateProfilePictureUseCase
import com.company.khomasi.domain.use_case.remote_user.UpdateUserUseCase
import com.company.khomasi.domain.use_case.remote_user.UserFavouriteUseCase
import com.company.khomasi.utils.Constants.BASE_URL
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
            connectTimeout(1, TimeUnit.MINUTES) // connect timeout
            readTimeout(30, TimeUnit.SECONDS) // socket timeout
            writeTimeout(15, TimeUnit.SECONDS) // write timeout
        }.build()
    }

    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteUserRepository(
        retrofitService: RetrofitService
    ): RemoteUserRepository = RemoteUserRepositoryImpl(retrofitService)

    @Provides
    @Singleton
    fun provideRemotePlaygroundRepository(
        retrofitService: RetrofitService
    ): RemotePlaygroundRepository = RemotePlaygroundRepositoryImpl(retrofitService)

    @Provides
    @Singleton
    fun provideAuthUseCases(
        remoteUserRepository: RemoteUserRepository
    ): AuthUseCases = AuthUseCases(
        confirmEmailUseCase = ConfirmEmailUseCase(remoteUserRepository),
        registerUseCase = RegisterUseCase(remoteUserRepository),
        loginUseCase = LoginUseCase(remoteUserRepository),
        getVerificationCodeUseCase = GetVerificationCodeUseCase(remoteUserRepository),
        recoverAccountUseCase = RecoverAccountUseCase(remoteUserRepository)
    )

    @Provides
    @Singleton
    fun provideRemoteUserUseCase(
        remoteUserRepository: RemoteUserRepository
    ): RemoteUserUseCase = RemoteUserUseCase(
        getPlaygroundsUseCase = GetPlaygroundsUseCase(remoteUserRepository),
        getUserFavoritePlaygroundsUseCase = GetUserFavoritePlaygroundsUseCase(remoteUserRepository),
        deleteUserFavoriteUseCase = DeleteUserFavouriteUseCase(remoteUserRepository),
        getUserBookingsUseCase = GetUserBookingsUseCase(remoteUserRepository),
        userFavouriteUseCase = UserFavouriteUseCase(remoteUserRepository),
        getSpecificPlaygroundUseCase = GetSpecificPlaygroundUseCase(remoteUserRepository),
        updateProfilePictureUseCase = UpdateProfilePictureUseCase(remoteUserRepository),
        updateUserUseCase = UpdateUserUseCase(remoteUserRepository),
        sendFeedbackUseCase = SendFeedbackUseCase(remoteUserRepository)
    )

    @Provides
    @Singleton
    fun provideRemotePlaygroundUseCase(
        remotePlaygroundRepository: RemotePlaygroundRepository
    ): RemotePlaygroundUseCase = RemotePlaygroundUseCase(
        getFreeTimeSlotsUseCase = GetFreeTimeSlotsUseCase(remotePlaygroundRepository),
    )
}