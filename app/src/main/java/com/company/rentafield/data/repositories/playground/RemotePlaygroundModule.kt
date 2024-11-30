package com.company.rentafield.data.repositories.playground

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemotePlaygroundModule {
    @Binds
    @Singleton
    abstract fun bindRemotePlaygroundRepository(
        remotePlaygroundRepositoryImpl: RemotePlaygroundRepositoryImpl
    ): RemotePlaygroundRepository
}