package com.company.rentafield.data.repositories.ai

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteAiModule {
    @Binds
    @Singleton
    abstract fun bindRemoteAiRepository(
        remoteAiRepositoryImpl: RemoteAiRepositoryImpl
    ): RemoteAiRepository
}