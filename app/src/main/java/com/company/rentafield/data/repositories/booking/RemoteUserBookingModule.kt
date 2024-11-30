package com.company.rentafield.data.repositories.booking

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteUserBookingModule {
    @Binds
    @Singleton
    abstract fun bindRemoteUserBooking(
        remoteUserBookingImpl: RemoteUserBookingImpl
    ): RemoteUserBooking
}