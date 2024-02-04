package com.example.jetpack_mvvm_sample.viewModel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.jetpack_mvvm_sample.di.DefaultDispatcher
import com.example.jetpack_mvvm_sample.listener.GpsStatusListener
import com.example.jetpack_mvvm_sample.listener.LocationListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationListener: LocationListener,
    private val gpsStatusListener: GpsStatusListener,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    val location: Flow<Location?> =
        locationListener.getLocation().map { location -> location }.flowOn(defaultDispatcher)

    val gpsStatus = gpsStatusListener

}