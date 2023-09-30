package com.example.jetpack_mvvm_sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack_mvvm_sample.R
import com.example.jetpack_mvvm_sample.databinding.LayoutRcvListBinding
import com.example.jetpack_mvvm_sample.model.Customer

class CustomerAdapter(private val dataSet: ArrayList<Customer>) :
    RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    private lateinit var binding: LayoutRcvListBinding
    private val dataSetFilter = ArrayList<Customer>().apply {
        addAll(dataSet)
    }

    class ViewHolder(private val binding: LayoutRcvListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: Customer) {
            binding.customer = customer
            binding.root.setOnLongClickListener {
                it.findNavController().navigate(R.id.action_fragment_list_to_fragment_customer)
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapter.ViewHolder {

        binding = LayoutRcvListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CustomerAdapter.ViewHolder, position: Int) {

        holder.bind(dataSet[position])

    }

    override fun getItemCount(): Int = dataSet.size

    fun gelFilter(): Filter {
        return customerFilter
    }

    private val customerFilter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {

            val filteredList: ArrayList<Customer> = ArrayList()

            if (p0.isNullOrEmpty()) {

                dataSetFilter.let { filteredList.addAll(it) }

            } else {

                val query = p0.toString().trim().lowercase()
                dataSetFilter.forEach {
                    if (it.name.lowercase().contains(query)) {
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
                dataSet.addAll(p1.values as ArrayList<Customer>)
                notifyDataSetChanged()

            }

        }

    }

}