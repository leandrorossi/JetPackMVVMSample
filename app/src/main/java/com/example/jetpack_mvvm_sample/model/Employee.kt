package com.example.jetpack_mvvm_sample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
class Employee {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = "name") var name: String? = null
    @ColumnInfo(name = "email") var email: String? = null
    @ColumnInfo(name = "age") var age: Int? = null
    @ColumnInfo(name = "gender") var gender: String? = null
    @ColumnInfo(name = "department") var department: String? = null
    @ColumnInfo(name = "admission") var admission: String? = null

}