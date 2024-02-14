package com.company.khomasi.di

import com.company.khomasi.domain.repository.AppRepository
import com.company.khomasi.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppUseCases(repository: AppRepository): AppUseCases {
        return AppUseCases(
            appRepository = repository,
        )
    }
}