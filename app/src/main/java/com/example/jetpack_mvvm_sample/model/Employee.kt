package com.example.jetpack_mvvm_sample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class Employee(

    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "age") var age: Int?,
    @ColumnInfo(name = "gender") var gender: String?,
    @ColumnInfo(name = "department") var department: String?,
    @ColumnInfo(name = "admission") var admission: String?

)