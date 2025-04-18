package com.company.rentafield.data.repositories.auth

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteUserAuthorizationModule {
    @Binds
    @Singleton
    abstract fun bindRemoteUserAuthorization(
        remoteUserAuthorizationImpl: RemoteUserAuthorizationImpl
    ): RemoteUserAuthorization
}