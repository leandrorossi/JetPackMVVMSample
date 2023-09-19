package com.example.jetpack_mvvm_sample.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jetpack_mvvm_sample.R
import com.example.jetpack_mvvm_sample.databinding.FragmentLocationBinding
import com.example.jetpack_mvvm_sample.viewModel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private var _binging: FragmentLocationBinding? = null
    private val binding get() = _binging!!

    private val viewModel: LocationViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binging = FragmentLocationBinding.inflate(inflater, container, false)

        binding.txtLatitude.text = "Latitude: 0.0"
        binding.txtLongitude.text = "Longitude: 0.0"
        binding.txtAccuracy.text = "Precisão: 0 m"

        lifecycleScope.launch {
            viewModel.location.collect { loc ->
                binding.txtLatitude.text = "Latitude: " + loc?.latitude.toString()
                binding.txtLongitude.text = "Longitude: " + loc?.longitude.toString()
                binding.txtAccuracy.text = "Precisão: " + loc?.accuracy.toString() + " m"
            }
        }

        viewModel.gpsStatus.observe(viewLifecycleOwner) { gps ->
            if (gps) {

                binding.txtGpsStatus.text = "Ligado"
                binding.imgGpsStatus.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_gps_on)

            } else {

                binding.txtLatitude.text = "Latitude: 0.0"
                binding.txtLongitude.text = "Longitude: 0.0"
                binding.txtAccuracy.text = "Precisão: 0 m"
                binding.txtGpsStatus.text = "Desligado"
                binding.imgGpsStatus.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_gps_off)

            }

        }

        binding.fab.setOnClickListener {
            val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null
    }

}