package com.example.jetpack_mvvm_sample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpack_mvvm_sample.R
import com.example.jetpack_mvvm_sample.adapter.CustomerAdapter
import com.example.jetpack_mvvm_sample.databinding.FragmentListBinding
import com.example.jetpack_mvvm_sample.viewModel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.rcvList.layoutManager = LinearLayoutManager(context)
        binding.rcvList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        //viewModel.insert()

        viewModel.customerLiveData.observe(viewLifecycleOwner) {customers ->
            val customerAdapter = CustomerAdapter(customers)
            binding.rcvList.adapter = customerAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_HomeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}