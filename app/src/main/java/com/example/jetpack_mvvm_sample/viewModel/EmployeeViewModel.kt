package com.example.jetpack_mvvm_sample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_mvvm_sample.model.Employee
import com.example.jetpack_mvvm_sample.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    private val _employeeLiveData = MutableLiveData<Employee>()
    val employeeLiveData : LiveData<Employee> get() = _employeeLiveData

    fun getEmployee(id: Int) {

        viewModelScope.launch {
            _employeeLiveData.postValue(employeeRepository.getByIdEmployee(id))
        }

    }

    fun insertEmployee(employee: Employee) {

        viewModelScope.launch {
            employeeRepository.insertEmployee(employee)
        }

    }

    fun updateEmployee(employee: Employee) {

        viewModelScope.launch {
            employeeRepository.updateEmployee(employee)
        }

    }

}