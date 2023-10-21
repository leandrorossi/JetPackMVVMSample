package com.example.jetpack_mvvm_sample.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jetpack_mvvm_sample.R
import com.example.jetpack_mvvm_sample.databinding.FragmentEmployeeBinding
import com.example.jetpack_mvvm_sample.model.Employee
import com.example.jetpack_mvvm_sample.viewModel.EmployeeViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeFragment : Fragment() {

    private var _binding: FragmentEmployeeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmployeeViewModel by viewModels()
    private val args: EmployeeFragmentArgs by navArgs()

    private var isFormCorrect = true

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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uIState.collect { state ->

                    state.employee?.let {

                        binding.edtName.setText(it.name)
                        binding.edtEmail.setText(it.email)
                        binding.edtAge.setText(it.age.toString())
                        binding.edtDepartment.setText(it.department, false)

                        when (it.gender) {
                            "Feminino" -> binding.radiogroupGender.check(binding.radiobutton1.id)
                            "Masculino" -> binding.radiogroupGender.check(binding.radiobutton2.id)
                        }

                    }

                    if (!state.isNameCorrect) {

                        isFormCorrect = false
                        binding.edtName.error = "Campo Obrigatório"

                    }
                    if (!state.isEmailCorrect) {

                        isFormCorrect = false
                        binding.edtEmail.error = "Campo Obrigatório"

                    }

                }

            }
        }

        binding.buttonSave.setOnClickListener {

            viewModel.checkForm(binding.edtName.text.toString(), binding.edtEmail.text.toString())

            if (!isFormCorrect) return@setOnClickListener

            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val age = binding.edtAge.text.toString().toInt()
            val department = binding.edtDepartment.text.toString()

            val gender = when (binding.radiogroupGender.checkedRadioButtonId) {
                R.id.radiobutton_1 -> binding.radiobutton1.text.toString()
                R.id.radiobutton_2 ->  binding.radiobutton2.text.toString()
                else -> null
            }

            if (isNewEmployee) {

                viewModel.insertEmployee(name, email, age, gender, department)

            } else {

                viewModel.updateEmployee(idEmployee, name, email, age, gender, department)

            }

            findNavController().popBackStack()

        }

        return binding.root
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