package com.example.jetpack_mvvm_sample.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetpack_mvvm_sample.model.Customer

@Dao
interface CustomerDao {

    @Insert
    suspend fun insert(vararg customer: Customer)

    @Update
    suspend fun update(vararg customer: Customer)

    @Delete
    suspend fun delete(vararg customer: Customer)

    @Query("SELECT * FROM customer WHERE id = :id")
    suspend fun findById(id: Int) : Customer

    @Query("SELECT * FROM customer")
    suspend fun findAll(): List<Customer>

}