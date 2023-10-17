package com.example.jetpack_mvvm_sample.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jetpack_mvvm_sample.R
import com.example.jetpack_mvvm_sample.databinding.FragmentEmployeeBinding
import com.example.jetpack_mvvm_sample.model.Employee
import com.example.jetpack_mvvm_sample.viewModel.EmployeeViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeFragment : Fragment() {

    private var _binding: FragmentEmployeeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmployeeViewModel by viewModels()
    private val args: EmployeeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEmployeeBinding.inflate(inflater, container, false)

        val isNewEmployee = args.isNewEmployee
        val idEmployee = args.idEmployee

        if (isNewEmployee) {

            binding.buttonSave.text = "Salvar"

        } else {

            binding.buttonSave.text = "Editar"
            binding.txtILDepartment.isExpandedHintEnabled = false

            viewModel.getEmployee(idEmployee)
            viewModel.employeeLiveData.observe(viewLifecycleOwner) { employee ->

                binding.edtName.setText(employee.name)
                binding.edtEmail.setText(employee.email)
                binding.edtAge.setText(employee.age.toString())
                binding.edtDepartment.setText(employee.department, false)

                when (employee.gender) {
                    "Feminino" -> binding.radiogroupGender.check(binding.radiobutton1.id)
                    "Masculino" -> binding.radiogroupGender.check(binding.radiobutton2.id)
                }

            }

        }

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.departments,
            R.layout.list_item
        )
        binding.edtDepartment.setAdapter(adapter)

        hintExpandedListener(binding.edtName, binding.txtILName)
        hintExpandedListener(binding.edtEmail, binding.txtILEmail)
        hintExpandedListener(binding.edtAge, binding.txtILAge)

        binding.buttonSave.setOnClickListener {

            if (!checkFields()) return@setOnClickListener

            val employee = Employee()
            employee.name = binding.edtName.text.toString()
            employee.email = binding.edtEmail.text.toString()
            employee.age = binding.edtAge.text.toString().toIntOrNull()
            employee.department = binding.edtDepartment.text.toString()

            when (binding.radiogroupGender.checkedRadioButtonId) {
                R.id.radiobutton_1 -> employee.gender = binding.radiobutton1.text.toString()
                R.id.radiobutton_2 -> employee.gender = binding.radiobutton2.text.toString()
                else -> employee.gender = null
            }

            if (isNewEmployee) {

                viewModel.insertEmployee(employee)

            } else {

                employee.id = idEmployee
                viewModel.updateEmployee(employee)

            }

            findNavController().popBackStack()

        }

        return binding.root
    }

    private fun checkFields(): Boolean {

        var isCorrect = true

        val name = binding.edtName.text
        val email = binding.edtEmail.text

        if (name.isNullOrEmpty()) {

            binding.edtName.error = "Campo Obrigatório"
            isCorrect = false

        }
        if (email.isNullOrEmpty()) {

            binding.edtEmail.error = "Campo Obrigatório"
            isCorrect = false

        }

        return isCorrect
    }

    private fun hintExpandedListener(
        textInputEditText: TextInputEditText,
        textInputLayout: TextInputLayout
    ) {

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                textInputLayout.isExpandedHintEnabled = p3 == 0

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}