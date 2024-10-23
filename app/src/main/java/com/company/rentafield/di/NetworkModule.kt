package com.company.rentafield.di


import com.company.rentafield.data.data_source.RetrofitAiService
import com.company.rentafield.data.data_source.RetrofitService
import com.company.rentafield.data.repository.RemoteAiRepositoryImpl
import com.company.rentafield.data.repository.RemotePlaygroundRepositoryImpl
import com.company.rentafield.data.repository.RemoteUserAuthorizationImpl
import com.company.rentafield.data.repository.RemoteUserBookingImpl
import com.company.rentafield.data.repository.RemoteUserPlaygroundImpl
import com.company.rentafield.data.repository.RemoteUserRepositoryImpl
import com.company.rentafield.domain.repository.RemoteAiRepository
import com.company.rentafield.domain.repository.RemotePlaygroundRepository
import com.company.rentafield.domain.repository.RemoteUserAuthorization
import com.company.rentafield.domain.repository.RemoteUserBooking
import com.company.rentafield.domain.repository.RemoteUserPlayground
import com.company.rentafield.domain.repository.RemoteUserRepository
import com.company.rentafield.domain.use_case.ai.AiUseCases
import com.company.rentafield.domain.use_case.ai.GetAiResultsUseCase
import com.company.rentafield.domain.use_case.ai.GetUploadStatusUseCase
import com.company.rentafield.domain.use_case.ai.UploadVideoUseCase
import com.company.rentafield.domain.use_case.auth.AuthUseCases
import com.company.rentafield.domain.use_case.auth.ConfirmEmailUseCase
import com.company.rentafield.domain.use_case.auth.GetVerificationCodeUseCase
import com.company.rentafield.domain.use_case.auth.LoginUseCase
import com.company.rentafield.domain.use_case.auth.RecoverAccountUseCase
import com.company.rentafield.domain.use_case.auth.RegisterUseCase
import com.company.rentafield.domain.use_case.remote_user.BookingPlaygroundUseCase
import com.company.rentafield.domain.use_case.remote_user.CancelBookingUseCase
import com.company.rentafield.domain.use_case.remote_user.DeleteUserFavouriteUseCase
import com.company.rentafield.domain.use_case.remote_user.GetFilteredPlaygroundsUseCase
import com.company.rentafield.domain.use_case.remote_user.GetFreeTimeSlotsUseCase
import com.company.rentafield.domain.use_case.remote_user.GetPlaygroundReviewsUseCase
import com.company.rentafield.domain.use_case.remote_user.GetPlaygroundsUseCase
import com.company.rentafield.domain.use_case.remote_user.GetProfileImageUseCase
import com.company.rentafield.domain.use_case.remote_user.GetSpecificPlaygroundUseCase
import com.company.rentafield.domain.use_case.remote_user.GetUserBookingsUseCase
import com.company.rentafield.domain.use_case.remote_user.GetUserFavoritePlaygroundsUseCase
import com.company.rentafield.domain.use_case.remote_user.PlaygroundReviewUseCase
import com.company.rentafield.domain.use_case.remote_user.RemotePlaygroundUseCase
import com.company.rentafield.domain.use_case.remote_user.RemoteUserUseCase
import com.company.rentafield.domain.use_case.remote_user.SendFeedbackUseCase
import com.company.rentafield.domain.use_case.remote_user.UpdateProfilePictureUseCase
import com.company.rentafield.domain.use_case.remote_user.UpdateUserUseCase
import com.company.rentafield.domain.use_case.remote_user.UserDataUseCase
import com.company.rentafield.domain.use_case.remote_user.UserFavouriteUseCase
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
    fun provideAiService(okHttpClient: OkHttpClient): RetrofitAiService {
        return Retrofit.Builder()
            .baseUrl(AI_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitAiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteUserAuthorization(
        retrofitService: RetrofitService
    ): RemoteUserAuthorization = RemoteUserAuthorizationImpl(retrofitService)

    @Provides
    @Singleton
    fun provideRemoteUserRepository(
        retrofitService: RetrofitService
    ): RemoteUserRepository = RemoteUserRepositoryImpl(retrofitService)

    @Provides
    @Singleton
    fun provideRemoteUserBooking(
        retrofitService: RetrofitService
    ): RemoteUserBooking = RemoteUserBookingImpl(retrofitService)

    @Provides
    @Singleton
    fun provideRemoteUserPlayground(
        retrofitService: RetrofitService
    ): RemoteUserPlayground = RemoteUserPlaygroundImpl(retrofitService)

    @Provides
    @Singleton
    fun provideRemotePlaygroundRepository(
        retrofitService: RetrofitService
    ): RemotePlaygroundRepository = RemotePlaygroundRepositoryImpl(retrofitService)

    @Provides
    @Singleton
    fun provideRemoteAiRepository(
        retrofitAiService: RetrofitAiService,
        retrofitService: RetrofitService
    ): RemoteAiRepository = RemoteAiRepositoryImpl(
        retrofitAiService,
        retrofitService
    )

    @Provides
    @Singleton
    fun provideAuthUseCases(
        remoteUserAuthorization: RemoteUserAuthorization
    ): AuthUseCases = AuthUseCases(
        confirmEmailUseCase = ConfirmEmailUseCase(remoteUserAuthorization),
        registerUseCase = RegisterUseCase(remoteUserAuthorization),
        loginUseCase = LoginUseCase(remoteUserAuthorization),
        getVerificationCodeUseCase = GetVerificationCodeUseCase(remoteUserAuthorization),
        recoverAccountUseCase = RecoverAccountUseCase(remoteUserAuthorization)
    )

    @Provides
    @Singleton
    fun provideRemoteUserUseCase(
        remoteUserRepository: RemoteUserRepository,
        remotePlaygroundsRepository: RemotePlaygroundRepository,
        remoteUserPlayground: RemoteUserPlayground,
        remoteUserBooking: RemoteUserBooking
    ): RemoteUserUseCase = RemoteUserUseCase(
        getPlaygroundsUseCase = GetPlaygroundsUseCase(remotePlaygroundsRepository),
        getUserFavoritePlaygroundsUseCase = GetUserFavoritePlaygroundsUseCase(remoteUserPlayground),
        deleteUserFavoriteUseCase = DeleteUserFavouriteUseCase(remoteUserPlayground),
        getUserBookingsUseCase = GetUserBookingsUseCase(remoteUserBooking),
        userFavouriteUseCase = UserFavouriteUseCase(remoteUserPlayground),
        getSpecificPlaygroundUseCase = GetSpecificPlaygroundUseCase(remoteUserPlayground),
        updateProfilePictureUseCase = UpdateProfilePictureUseCase(remoteUserRepository),
        updateUserUseCase = UpdateUserUseCase(remoteUserRepository),
        sendFeedbackUseCase = SendFeedbackUseCase(remoteUserRepository),
        getProfileImageUseCase = GetProfileImageUseCase(remoteUserRepository),
        cancelBookingUseCase = CancelBookingUseCase(remoteUserBooking),
        playgroundReviewUseCase = PlaygroundReviewUseCase(remoteUserPlayground),
        userDataUseCase = UserDataUseCase(remoteUserRepository)
    )

    @Provides
    @Singleton
    fun provideRemotePlaygroundUseCase(
        remotePlaygroundRepository: RemotePlaygroundRepository
    ): RemotePlaygroundUseCase = RemotePlaygroundUseCase(
        getFreeTimeSlotsUseCase = GetFreeTimeSlotsUseCase(remotePlaygroundRepository),
        getPlaygroundReviewsUseCase = GetPlaygroundReviewsUseCase(remotePlaygroundRepository),
        getFilteredPlaygroundsUseCase = GetFilteredPlaygroundsUseCase(remotePlaygroundRepository),
        bookingPlaygroundUseCase = BookingPlaygroundUseCase(remotePlaygroundRepository)
    )

    @Provides
    @Singleton
    fun provideRemoteAiUseCase(
        remoteAiRepository: RemoteAiRepository,
        remoteUserRepository: RemoteUserRepository
    ): AiUseCases = AiUseCases(
        uploadVideoUseCase = UploadVideoUseCase(remoteAiRepository),
        getAiResultsUseCase = GetAiResultsUseCase(remoteAiRepository),
        getUploadStatusUseCase = GetUploadStatusUseCase(remoteUserRepository)
    )
}