package com.example.jetpack_mvvm_sample.di

import android.content.Context
import androidx.room.Room
import com.example.jetpack_mvvm_sample.AppDatabase
import com.example.jetpack_mvvm_sample.dao.CustomerDao
import com.example.jetpack_mvvm_sample.listener.GpsStatusListener
import com.example.jetpack_mvvm_sample.listener.LocationListener
import com.example.jetpack_mvvm_sample.viewModel.LocationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

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