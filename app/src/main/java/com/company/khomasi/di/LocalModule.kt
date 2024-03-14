package com.company.khomasi.di

import android.app.Application
import androidx.room.Room
import com.company.khomasi.data.data_source.database.AppDatabase
import com.company.khomasi.data.repository.LocalUserRepositoryImpl
import com.company.khomasi.domain.repository.LocalUserRepository
import com.company.khomasi.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasi.domain.use_case.app_entry.ReadAppEntry
import com.company.khomasi.domain.use_case.app_entry.SaveAppEntry
import com.company.khomasi.domain.use_case.app_entry.SaveIsLogin
import com.company.khomasi.domain.use_case.local_user.GetLocalUser
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.local_user.SaveLocalUser
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
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

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
        saveLocalUser = SaveLocalUser(localUserManger)
    )
}