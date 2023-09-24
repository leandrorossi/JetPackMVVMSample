package com.example.jetpack_mvvm_sample

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpack_mvvm_sample.dao.CustomerDao
import com.example.jetpack_mvvm_sample.model.Customer

@Database(entities = [Customer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao

}