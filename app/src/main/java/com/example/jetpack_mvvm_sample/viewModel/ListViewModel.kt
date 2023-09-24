package com.example.jetpack_mvvm_sample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_mvvm_sample.model.Customer
import com.example.jetpack_mvvm_sample.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {

    private val _customerLiveData = MutableLiveData<List<Customer>>()
    val customerLiveData: LiveData<List<Customer>> get() = _customerLiveData

    init {
        getCustomers()
    }

    private fun getCustomers() {

        viewModelScope.launch {
            _customerLiveData.value = customerRepository.getAllCustomer()
        }

    }

    fun insert() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                for (i in 2..1000) {
                    val customer = Customer(i, "Jhon", "jhon@gmail.com", 20)
                    customerRepository.insertCustomer(customer)
                }
                //customer?.let { customerRepository.insertCustomer(it) }

            }
        }

    }

}