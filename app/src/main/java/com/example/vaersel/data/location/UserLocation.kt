package com.example.vaersel.data.location

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class UserLocation(activity: ComponentActivity) {
    var lat: Double = 0.0
    var lon: Double = 0.0

    init{
        if(lon == 0.0 && lat == 0.0){
            getUserLocation(activity)
        }
    }
    private fun getUserLocation(activity: ComponentActivity) {


        val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

        val task = fusedLocationProviderClient.lastLocation
        if (ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }
        task.addOnSuccessListener {
            if(it != null) {
                lon = it.longitude
                lat = it.latitude
            }
        }
        task.addOnFailureListener{
            if(lat == 0.0) {
                getUserLocation(activity)
            }
        }
    }
}

