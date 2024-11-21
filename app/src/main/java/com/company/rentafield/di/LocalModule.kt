package com.company.rentafield.di

import android.app.Application
import com.company.rentafield.data.repositories.localuser.LocalUserRepository
import com.company.rentafield.data.repositories.localuser.LocalUserRepositoryImpl
import com.company.rentafield.domain.usecases.entry.AppEntryUseCases
import com.company.rentafield.domain.usecases.entry.ReadAppEntry
import com.company.rentafield.domain.usecases.entry.SaveAppEntry
import com.company.rentafield.domain.usecases.entry.SaveIsLogin
import com.company.rentafield.domain.usecases.localuser.GetLocalUser
import com.company.rentafield.domain.usecases.localuser.GetSearchHistory
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.localuser.RemoveSearchHistory
import com.company.rentafield.domain.usecases.localuser.SaveLocalUser
import com.company.rentafield.domain.usecases.localuser.SaveSearchHistory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideLocalUserRepository(
        application: Application
    ): LocalUserRepository = LocalUserRepositoryImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserRepository
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger),
        saveIsLogin = SaveIsLogin(localUserManger)
    )

    @Provides
    @Singleton
    fun provideLocalUserUseCases(
        localUserManger: LocalUserRepository
    ): LocalUserUseCases = LocalUserUseCases(
        getLocalUser = GetLocalUser(localUserManger),
        saveLocalUser = SaveLocalUser(localUserManger),
        getSearchHistory = GetSearchHistory(localUserManger),
        saveSearchHistory = SaveSearchHistory(localUserManger),
        removeSearchHistory = RemoveSearchHistory(localUserManger),
    )
}

