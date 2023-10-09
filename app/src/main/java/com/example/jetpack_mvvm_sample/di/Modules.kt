package com.example.jetpack_mvvm_sample.di

import android.content.Context
import com.example.jetpack_mvvm_sample.listener.GpsStatusListener
import com.example.jetpack_mvvm_sample.listener.LocationListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocation(
        @ApplicationContext context: Context,
    ): LocationListener = LocationListener(context)

}

@Module
@InstallIn(SingletonComponent::class)
object GpsStatusModule {

    @Provides
    @Singleton
    fun provideGpsStatus(
        @ApplicationContext context: Context,
    ): GpsStatusListener = GpsStatusListener(context)

}