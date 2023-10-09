package com.example.jetpack_mvvm_sample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_mvvm_sample.model.Employee
import com.example.jetpack_mvvm_sample.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val employeeRepository: EmployeeRepository) :
    ViewModel() {

    private val _employeeLiveData = MutableLiveData<List<Employee>>()
    val employeeLiveData: LiveData<List<Employee>> get() = _employeeLiveData

    init {
        getEmployees()
    }

    private fun getEmployees() {

        viewModelScope.launch {
            _employeeLiveData.value = employeeRepository.getAllEmployee()
        }

    }

    fun insert() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                for (i in 2..1000) {
                    val employee = Employee(i, "Jhon", "jhon@gmail.com", 20, "Masculino", "Department", null)
                    employeeRepository.insertEmployee(employee)
                }
            }
        }

    }

}