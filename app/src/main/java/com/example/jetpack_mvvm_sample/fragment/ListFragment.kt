package com.example.jetpack_mvvm_sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpack_mvvm_sample.R
import com.example.jetpack_mvvm_sample.adapter.EmployeeAdapter
import com.example.jetpack_mvvm_sample.databinding.FragmentListBinding
import com.example.jetpack_mvvm_sample.model.Employee
import com.example.jetpack_mvvm_sample.viewModel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()
    private var employeeAdapter: EmployeeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)

        setMenu()

        binding.fabList.setOnClickListener {
            val action = ListFragmentDirections.actionFragmentListToFragmentEmployee(true)
            findNavController().navigate(action)
        }

        binding.rcvList.layoutManager = LinearLayoutManager(context)
        binding.rcvList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.getEmployees()
        viewModel.employeeLiveData.observe(viewLifecycleOwner) { employees ->

            employeeAdapter = EmployeeAdapter(employees as ArrayList<Employee>)

            if (employeeAdapter?.itemCount == 0) {

                binding.imageEmptyList.visibility = View.VISIBLE
                binding.textviewEmptyList.visibility = View.VISIBLE

            }

            binding.rcvList.adapter = employeeAdapter

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_HomeFragment)
        }*/
    }

    private fun setMenu() {

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.search_item, menu)

                val searchItem = menu.findItem(R.id.search)

                val searchView: SearchView = searchItem.actionView as SearchView
                searchView.queryHint = "Pesquisar"

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        employeeAdapter?.gelFilter()?.filter(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        employeeAdapter?.gelFilter()?.filter(newText)
                        return true
                    }
                })

                searchView.setOnQueryTextFocusChangeListener { _, p1 ->
                    if (!p1 && searchView.query.isEmpty()) {
                        searchItem.collapseActionView()
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                        return true
                    }

                    else -> true
                }

            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        employeeAdapter = null
    }

}