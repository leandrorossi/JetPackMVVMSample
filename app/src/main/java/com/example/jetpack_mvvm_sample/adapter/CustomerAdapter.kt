package com.example.jetpack_mvvm_sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack_mvvm_sample.databinding.LayoutRcvListBinding
import com.example.jetpack_mvvm_sample.model.Customer

class CustomerAdapter(private val dataSet: List<Customer>) :
    RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    private lateinit var binding: LayoutRcvListBinding

    class ViewHolder(private val binding: LayoutRcvListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: Customer) {
            binding.customer = customer
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

}