package com.example.adacapstone.interfaces

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.adacapstone.utils.Permissions
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

interface Location {

    fun getCurrentLatLong(context: Context, activity: Activity): Array<Double> {
        val locationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        var currentLatitude: Double = 0.0
        var currentLongitude: Double = 0.0

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Permissions.LOCATION_REQUEST_CODE
            )
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude
                } else {
                    Toast.makeText(context, "Failed to retrieve lat-longs", Toast.LENGTH_SHORT).show()
                }
            }

        return arrayOf(currentLatitude, currentLongitude)
    }

}