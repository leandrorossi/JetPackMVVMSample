package com.example.jetpack_mvvm_sample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_mvvm_sample.di.IODispatcher
import com.example.jetpack_mvvm_sample.model.Employee
import com.example.jetpack_mvvm_sample.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private val _employeeLiveData = MutableLiveData<List<Employee>>()
    val employeeLiveData: LiveData<List<Employee>> get() = _employeeLiveData

    fun getEmployees() {

        viewModelScope.launch(ioDispatcher) {
            _employeeLiveData.postValue(employeeRepository.getAllEmployee())
        }

    }

}