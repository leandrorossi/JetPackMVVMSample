package com.example.jetpack_mvvm_sample.listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.lifecycle.LiveData

class GpsStatusListener(private val context: Context) : LiveData<Boolean>() {

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) = checkGps()
    }

    override fun onActive() {
        super.onActive()
        registerReceiver()
        checkGps()
    }

    override fun onInactive() {
        super.onInactive()
        unregisterReceiver()
    }

    private fun checkGps() = if (isLocationEnabled()) {
        postValue(true)
    } else {
        postValue(false)
    }

    private fun isLocationEnabled(): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun registerReceiver() =
        context.registerReceiver(receiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))

    private fun unregisterReceiver() = context.unregisterReceiver(receiver)

}