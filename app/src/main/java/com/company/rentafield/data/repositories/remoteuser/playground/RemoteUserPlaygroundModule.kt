package com.company.rentafield.data.repositories.remoteuser.playground

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteUserPlaygroundModule {
    @Binds
    @Singleton
    abstract fun bindRemoteUserPlayground(
        remoteUserPlaygroundImpl: RemotePlaygroundUserRepositoryImpl
    ): RemotePlaygroundUserRepository
}