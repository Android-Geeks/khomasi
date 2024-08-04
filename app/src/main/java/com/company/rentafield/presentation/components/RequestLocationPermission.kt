package com.company.rentafield.presentation.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.company.rentafield.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.concurrent.TimeUnit

/**
 * Manages all location related tasks for the app.
 */

data class LatandLong(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

//A callback for receiving notifications from the FusedLocationProviderClient.
lateinit var locationCallback: LocationCallback

//The main entry point for interacting with the Fused Location Provider
lateinit var locationProvider: FusedLocationProviderClient

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun getUserLocation(context: Context): LatandLong {

    // The Fused Location Provider provides access to location APIs.
    locationProvider = LocationServices.getFusedLocationProviderClient(context)

    var currentUserLocation by remember { mutableStateOf(LatandLong()) }

    var showRationalState by remember { mutableStateOf(true) }

    var showAlertDialog by remember { mutableStateOf(false) }

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    if (showAlertDialog && !permissionsState.allPermissionsGranted) {
        MyAlertDialog(
            title = R.string.location_permission_required,
            text = R.string.location_permission_message,
            onDismissRequest = {},
            confirmButtonText = R.string.go_to_settings,
            onConfirmButtonClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            },
            confirmButtonColor = MaterialTheme.colorScheme.primary
        )
    }

    DisposableEffect(key1 = locationProvider) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {

                /**
                 * Option 1
                 * This option returns the locations computed, ordered from oldest to newest.
                 * */
                for (location in result.locations) {
                    // Update data class with location data
                    currentUserLocation = LatandLong(location.latitude, location.longitude)
                    Log.d("LOCATION_TAG", "${location.latitude},${location.longitude}")
                }


                /**
                 * Option 2
                 * This option returns the most recent historical location currently available.
                 * Will return null if no historical location is available
                 * */
                locationProvider.lastLocation
                    .addOnSuccessListener { location ->
                        location?.let {
                            val lat = location.latitude
                            val long = location.longitude
                            // Update data class with location data
                            currentUserLocation = LatandLong(latitude = lat, longitude = long)
                        }
                    }
                    .addOnFailureListener {
                        Log.e("LOCATION_TAG", "${it.message}")
                    }

            }
        }
        if (permissionsState.allPermissionsGranted) {
            locationUpdate()
        } else {
            if (permissionsState.shouldShowRationale || showRationalState) {
                permissionsState.launchMultiplePermissionRequest()
                showRationalState = false
            } else {
                showAlertDialog = true
            }
        }

        onDispose {
            stopLocationUpdate()
        }
    }
    //
    return currentUserLocation

}

fun stopLocationUpdate() {
    try {
        //Removes all location updates for the given callback.
        val removeTask = locationProvider.removeLocationUpdates(locationCallback)
        removeTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("LOCATION_TAG", "Location Callback removed.")
            } else {
                Log.d("LOCATION_TAG", "Failed to remove Location Callback.")
            }
        }
    } catch (se: SecurityException) {
        Log.e("LOCATION_TAG", "Failed to remove Location Callback.. $se")
    }
}

@SuppressLint("MissingPermission")
fun locationUpdate() {
    locationCallback.let {
        //An encapsulation of various parameters for requesting
        // location through FusedLocationProviderClient.

        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(60))
                .build()

        //use FusedLocationProviderClient to request location update
        locationProvider.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}