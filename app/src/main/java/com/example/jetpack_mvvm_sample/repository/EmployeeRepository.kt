package com.example.jetpack_mvvm_sample.repository

import com.example.jetpack_mvvm_sample.dao.EmployeeDao
import com.example.jetpack_mvvm_sample.model.Employee
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val employeeDao: EmployeeDao) {

    suspend fun insertEmployee(employee: Employee) = employeeDao.insert(employee)

    suspend fun updateEmployee(employee: Employee) = employeeDao.update(employee)

    suspend fun getAllEmployee(): List<Employee> = employeeDao.findAll()

    suspend fun getByIdEmployee(id: Int): Employee = employeeDao.findById(id)

}