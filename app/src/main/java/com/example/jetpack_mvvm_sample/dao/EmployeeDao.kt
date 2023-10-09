package com.example.jetpack_mvvm_sample.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetpack_mvvm_sample.model.Employee

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insert(vararg employee: Employee)

    @Update
    suspend fun update(vararg employee: Employee)

    @Delete
    suspend fun delete(vararg employee: Employee)

    @Query("SELECT * FROM employee WHERE id = :id")
    suspend fun findById(id: Int) : Employee

    @Query("SELECT * FROM employee")
    suspend fun findAll(): List<Employee>

}