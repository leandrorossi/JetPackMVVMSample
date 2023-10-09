package com.example.jetpack_mvvm_sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack_mvvm_sample.R
import com.example.jetpack_mvvm_sample.databinding.LayoutRcvListBinding
import com.example.jetpack_mvvm_sample.model.Employee

class EmployeeAdapter(private val dataSet: ArrayList<Employee>) :
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    private lateinit var binding: LayoutRcvListBinding
    private val dataSetFilter = ArrayList<Employee>().apply {
        addAll(dataSet)
    }

    class ViewHolder(private val binding: LayoutRcvListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.employee = employee
            binding.root.setOnLongClickListener {
                it.findNavController().navigate(R.id.action_fragment_list_to_fragment_employee)
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter.ViewHolder {

        binding = LayoutRcvListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: EmployeeAdapter.ViewHolder, position: Int) {

        holder.bind(dataSet[position])

    }

    override fun getItemCount(): Int = dataSet.size

    fun gelFilter(): Filter {
        return employeeFilter
    }

    private val employeeFilter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {

            val filteredList: ArrayList<Employee> = ArrayList()

            if (p0.isNullOrEmpty()) {

                dataSetFilter.let { filteredList.addAll(it) }

            } else {

                val query = p0.toString().trim().lowercase()
                dataSetFilter.forEach {
                    if (it.name?.lowercase()?.contains(query) == true) {
                        filteredList.add(it)
                    }
                }

            }

            val results = FilterResults()
            results.values = filteredList
            return results

        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

            if (p1?.values is ArrayList<*>) {

                dataSet.clear()
                dataSet.addAll(p1.values as ArrayList<Employee>)
                notifyDataSetChanged()

            }

        }

    }

}