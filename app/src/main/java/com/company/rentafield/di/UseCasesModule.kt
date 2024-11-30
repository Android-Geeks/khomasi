package com.company.rentafield.di

import com.company.rentafield.data.repositories.ai.RemoteAiRepository
import com.company.rentafield.data.repositories.auth.RemoteUserAuthorization
import com.company.rentafield.data.repositories.booking.RemoteUserBooking
import com.company.rentafield.data.repositories.playground.RemotePlaygroundRepository
import com.company.rentafield.data.repositories.remoteuser.playground.RemotePlaygroundUserRepository
import com.company.rentafield.data.repositories.remoteuser.user.RemoteUserRepository
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
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.SendFeedbackUseCase
import com.company.rentafield.domain.usecases.remoteuser.UpdateProfilePictureUseCase
import com.company.rentafield.domain.usecases.remoteuser.UpdateUserUseCase
import com.company.rentafield.domain.usecases.remoteuser.UserDataUseCase
import com.company.rentafield.domain.usecases.remoteuser.UserFavouriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

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
        remotePlaygroundUserRepository: RemotePlaygroundUserRepository,
        remoteUserBooking: RemoteUserBooking
    ): RemoteUserUseCases = RemoteUserUseCases(
        getPlaygroundsUseCase = GetPlaygroundsUseCase(remotePlaygroundsRepository),
        getUserFavoritePlaygroundsUseCase = GetUserFavoritePlaygroundsUseCase(remotePlaygroundUserRepository),
        deleteUserFavoriteUseCase = DeleteUserFavouriteUseCase(remotePlaygroundUserRepository),
        getUserBookingsUseCase = GetUserBookingsUseCase(remoteUserBooking),
        userFavouriteUseCase = UserFavouriteUseCase(remotePlaygroundUserRepository),
        getSpecificPlaygroundUseCase = GetSpecificPlaygroundUseCase(remotePlaygroundsRepository),
        updateProfilePictureUseCase = UpdateProfilePictureUseCase(remoteUserRepository),
        updateUserUseCase = UpdateUserUseCase(remoteUserRepository),
        sendFeedbackUseCase = SendFeedbackUseCase(remoteUserRepository),
        getProfileImageUseCase = GetProfileImageUseCase(remoteUserRepository),
        cancelBookingUseCase = CancelBookingUseCase(remoteUserBooking),
        playgroundReviewUseCase = PlaygroundReviewUseCase(remotePlaygroundUserRepository),
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