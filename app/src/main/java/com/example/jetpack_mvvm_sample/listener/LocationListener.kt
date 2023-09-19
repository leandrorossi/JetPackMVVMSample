package com.example.jetpack_mvvm_sample.listener

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LocationListener(
    private val context: Context,
) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
            .build()
    }

    @SuppressLint("MissingPermission")
    private val _locationListener = callbackFlow {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    trySend(location)
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            createLocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )

        awaitClose {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }

    }

    fun getLocation(): Flow<Location?> {
        return _locationListener
    }

}
