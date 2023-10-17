package com.example.jetpack_mvvm_sample

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.example.jetpack_mvvm_sample.dao.EmployeeDao
import com.example.jetpack_mvvm_sample.model.Employee

@Database(
    entities = [Employee::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 2, to = 3)]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    @RenameTable(fromTableName = "customer", toTableName = "employee")
    class MyAutoMigration : AutoMigrationSpec

}
