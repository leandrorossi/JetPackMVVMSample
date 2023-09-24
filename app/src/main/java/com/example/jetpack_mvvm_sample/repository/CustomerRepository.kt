package com.example.jetpack_mvvm_sample.repository

import com.example.jetpack_mvvm_sample.dao.CustomerDao
import com.example.jetpack_mvvm_sample.model.Customer
import javax.inject.Inject

class CustomerRepository @Inject constructor(private val customerDao: CustomerDao) {

    suspend fun insertCustomer(customer: Customer) {
        customerDao.insert(customer)
    }

    suspend fun getAllCustomer(): List<Customer> = customerDao.findAll()

}