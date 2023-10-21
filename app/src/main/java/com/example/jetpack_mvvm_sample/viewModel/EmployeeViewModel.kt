package com.example.jetpack_mvvm_sample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_mvvm_sample.model.Employee
import com.example.jetpack_mvvm_sample.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EmployeeUiState(
    val isNameCorrect: Boolean = true,
    val isEmailCorrect: Boolean = true,
    val employee: Employee? = null
)

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    private val _uIState = MutableStateFlow(EmployeeUiState())
    val uIState: StateFlow<EmployeeUiState> get() = _uIState

    fun getEmployee(id: Int) {

        viewModelScope.launch {
            _uIState.update { employeeUiState ->
                employeeUiState.copy(employee = employeeRepository.getByIdEmployee(id))
            }
        }

    }

    fun insertEmployee(
        name: String,
        email: String,
        age: Int?,
        gender: String?,
        department: String?
    ) {

        viewModelScope.launch {

            val employee = Employee()
            employee.name = name
            employee.email = email
            employee.age = age
            employee.gender = gender
            employee.department = department

            employeeRepository.insertEmployee(employee)

        }

    }

    fun updateEmployee(
        id: Int,
        name: String,
        email: String,
        age: Int?,
        gender: String?,
        department: String?
    ) {

        viewModelScope.launch {

            val employee = Employee()
            employee.id = id
            employee.name = name
            employee.email = email
            employee.age = age
            employee.gender = gender
            employee.department = department

            employeeRepository.updateEmployee(employee)

        }

    }

    fun checkForm(name: String?, email: String?) {

        if (name.isNullOrEmpty()) {
            _uIState.update { employeeUiState -> employeeUiState.copy(isNameCorrect = false) }
        }
        if (email.isNullOrEmpty()) {
            _uIState.update { employeeUiState -> employeeUiState.copy(isEmailCorrect = false) }
        }

    }

}