package com.company.rentafield.di


import com.company.rentafield.data.repositories.ai.RemoteAiRepository
import com.company.rentafield.data.repositories.ai.RemoteAiRepositoryImpl
import com.company.rentafield.data.repositories.auth.RemoteUserAuthorization
import com.company.rentafield.data.repositories.auth.RemoteUserAuthorizationImpl
import com.company.rentafield.data.repositories.booking.RemoteUserBooking
import com.company.rentafield.data.repositories.booking.RemoteUserBookingImpl
import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepository
import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepositoryImpl
import com.company.rentafield.data.repositories.remoteuser.playground.RemoteUserPlayground
import com.company.rentafield.data.repositories.remoteuser.playground.RemoteUserPlaygroundImpl
import com.company.rentafield.data.repositories.remoteuser.user.RemoteUserRepository
import com.company.rentafield.data.repositories.remoteuser.user.RemoteUserRepositoryImpl
import com.company.rentafield.data.services.RetrofitAiService
import com.company.rentafield.data.services.RetrofitAiStatusService
import com.company.rentafield.data.services.RetrofitAuthService
import com.company.rentafield.data.services.RetrofitPlaygroundService
import com.company.rentafield.data.services.RetrofitUserService
import com.company.rentafield.domain.usecases.ai.AiUseCases
import com.company.rentafield.domain.usecases.ai.GetAiResultsUseCase
import com.company.rentafield.domain.usecases.ai.GetUploadStatusUseCase
import com.company.rentafield.domain.usecases.ai.UploadVideoUseCase
import com.company.rentafield.domain.usecases.auth.AuthUseCases
import com.company.rentafield.domain.usecases.auth.ConfirmEmailUseCase
import com.company.rentafield.domain.usecases.auth.GetVerificationCodeUseCase
import com.company.rentafield.domain.usecases.auth.LoginUseCase
import com.company.rentafield.domain.usecases.auth.RecoverAccountUseCase
import com.company.rentafield.domain.usecases.auth.RegisterUseCase
import com.company.rentafield.domain.usecases.remoteuser.BookingPlaygroundUseCase
import com.company.rentafield.domain.usecases.remoteuser.CancelBookingUseCase
import com.company.rentafield.domain.usecases.remoteuser.DeleteUserFavouriteUseCase
import com.company.rentafield.domain.usecases.remoteuser.GetFilteredPlaygroundsUseCase
import com.company.rentafield.domain.usecases.remoteuser.GetFreeTimeSlotsUseCase
import com.company.rentafield.domain.usecases.remoteuser.GetPlaygroundReviewsUseCase
import com.company.rentafield.domain.usecases.remoteuser.GetPlaygroundsUseCase
import com.company.rentafield.domain.usecases.remoteuser.GetProfileImageUseCase
import com.company.rentafield.domain.usecases.remoteuser.GetSpecificPlaygroundUseCase
import com.company.rentafield.domain.usecases.remoteuser.GetUserBookingsUseCase
import com.company.rentafield.domain.usecases.remoteuser.GetUserFavoritePlaygroundsUseCase
import com.company.rentafield.domain.usecases.remoteuser.PlaygroundReviewUseCase
import com.company.rentafield.domain.usecases.remoteuser.RemotePlaygroundUseCase
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCase
import com.company.rentafield.domain.usecases.remoteuser.SendFeedbackUseCase
import com.company.rentafield.domain.usecases.remoteuser.UpdateProfilePictureUseCase
import com.company.rentafield.domain.usecases.remoteuser.UpdateUserUseCase
import com.company.rentafield.domain.usecases.remoteuser.UserDataUseCase
import com.company.rentafield.domain.usecases.remoteuser.UserFavouriteUseCase
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

    private inline fun <reified T> provideRetrofitService(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(T::class.java)
    }

    @Provides
    @Singleton
    fun provideAiService(okHttpClient: OkHttpClient): RetrofitAiService {
        return provideRetrofitService(okHttpClient, AI_URL)
    }

    @Provides
    @Singleton
    fun provideRetrofitAuthService(okHttpClient: OkHttpClient): RetrofitAuthService {
        return provideRetrofitService(okHttpClient, BASE_URL)
    }

    @Provides
    @Singleton
    fun provideRetrofitUserService(okHttpClient: OkHttpClient): RetrofitUserService {
        return provideRetrofitService(okHttpClient, BASE_URL)
    }

    @Provides
    @Singleton
    fun provideRetrofitPlaygroundService(okHttpClient: OkHttpClient): RetrofitPlaygroundService {
        return provideRetrofitService(okHttpClient, BASE_URL)
    }

    @Provides
    @Singleton
    fun provideRetrofitAiStatusService(okHttpClient: OkHttpClient): RetrofitAiStatusService {
        return provideRetrofitService(okHttpClient, BASE_URL)
    }

    @Provides
    @Singleton
    fun provideRemoteUserAuthorization(
        retrofitAuthService: RetrofitAuthService
    ): RemoteUserAuthorization = RemoteUserAuthorizationImpl(retrofitAuthService)

    @Provides
    @Singleton
    fun provideRemoteUserRepository(
        retrofitUserService: RetrofitUserService
    ): RemoteUserRepository = RemoteUserRepositoryImpl(retrofitUserService)

    @Provides
    @Singleton
    fun provideRemoteUserBooking(
        retrofitUserService: RetrofitUserService
    ): RemoteUserBooking = RemoteUserBookingImpl(retrofitUserService)

    @Provides
    @Singleton
    fun provideRemoteUserPlayground(
        retrofitUserService: RetrofitUserService
    ): RemoteUserPlayground = RemoteUserPlaygroundImpl(retrofitUserService)

    @Provides
    @Singleton
    fun provideRemotePlaygroundRepository(
        retrofitPlaygroundService: RetrofitPlaygroundService
    ): RemotePlaygroundRepository = RemotePlaygroundRepositoryImpl(retrofitPlaygroundService)

    @Provides
    @Singleton
    fun provideRemoteAiRepository(
        retrofitAiService: RetrofitAiService,
        retrofitUserService: RetrofitUserService
    ): RemoteAiRepository = RemoteAiRepositoryImpl(
        retrofitAiService,
        retrofitUserService
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
        getSpecificPlaygroundUseCase = GetSpecificPlaygroundUseCase(remotePlaygroundsRepository),
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