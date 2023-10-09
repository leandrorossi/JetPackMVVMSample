package com.example.jetpack_mvvm_sample.di

import android.content.Context
import androidx.room.Room
import com.example.jetpack_mvvm_sample.AppDatabase
import com.example.jetpack_mvvm_sample.dao.EmployeeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()

    @Provides
    fun provideEmployeeDao(appDatabase: AppDatabase): EmployeeDao = appDatabase.employeeDao()

}