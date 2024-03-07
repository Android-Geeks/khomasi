package com.company.khomasi.di

import android.app.Application
import androidx.room.Room
import com.company.khomasi.data.data_source.local.database.AppDatabase
import com.company.khomasi.data.data_source.local.local_user.LocalUserRepositoryImpl
import com.company.khomasi.data.repository.LocationRepositoryImpl
import com.company.khomasi.domain.repository.LocalUserRepository
import com.company.khomasi.domain.repository.LocationRepository
import com.company.khomasi.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasi.domain.use_case.app_entry.ReadAppEntry
import com.company.khomasi.domain.use_case.app_entry.SaveAppEntry
import com.company.khomasi.domain.use_case.app_entry.SaveIsLogin
import com.company.khomasi.domain.use_case.location.GetCurrentLocation
import com.company.khomasi.domain.use_case.location.GetLastUserLocation
import com.company.khomasi.domain.use_case.location.LocationUseCases
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    fun provideFusedLocationClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationRepository = LocationRepositoryImpl(fusedLocationProviderClient, application)

    @Provides
    @Singleton
    fun provideLocationUseCases(
        locationRepository: LocationRepository
    ): LocationUseCases = LocationUseCases(
        getCurrentLocation = GetCurrentLocation(locationRepository),
        getLastUserLocation = GetLastUserLocation(locationRepository)
    )

}