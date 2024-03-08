package com.company.khomasi.presentation.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.RequestLocationPermission
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onDoneClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState = viewModel.uiState.collectAsState().value
    Box {
        AuthSheet(
            screenContent = {
                Image(
                    painter =
                    if (isSystemInDarkTheme())
                        painterResource(id = R.drawable.dark_starting_player)
                    else
                        painterResource(id = R.drawable.light_starting_player),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            sheetContent = {
                RegisterDataPage(
                    viewModel = viewModel,
                    onLoginClick = onLoginClick,
                    onBack = onBack
                )
            }
        )
        when (val registerState = viewModel.registerState.collectAsState().value) {
            is DataState.Loading -> {
                Loading()
                Log.d("RegisterDataPage", "Loading: ")
            }

            is DataState.Success -> {
                onDoneClick()
                Log.d("RegisterDataPage", "Success")
            }

            is DataState.Error -> {
                Log.d("RegisterDataPage", "Error: $registerState")
            }

            is DataState.Empty -> {
                Log.d("RegisterDataPage", "Empty")
            }
        }
    }
    if (viewModel.uiState.value.longitude == null)
        RequestLocationPermission(
            onPermissionGranted = {
                // Attempt to get the last known user location
                viewModel.getLastUserLocation(
                    onGetLastLocationSuccess = viewModel::updateLocation,
                    onGetLastLocationFailed = {
                        Toast.makeText(
                            context,
                            it.localizedMessage ?: "Error Getting Last Location",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    onGetLastLocationIsNull = {
                        // Attempt to get the current user location
                        viewModel.getCurrentLocation(
                            onGetCurrentLocationSuccess = viewModel::updateLocation,
                            onGetCurrentLocationFailed = {
                                Toast.makeText(
                                    context,
                                    it.localizedMessage ?: "Error Getting Last Location",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            priority = true
                        )
                    }
                )
            },
            onPermissionDenied = {
                Toast.makeText(
                    context,
                    "Please Accept Location Permission, To Enjoy All Features",
                    Toast.LENGTH_LONG
                ).show()
            },
            onPermissionsRevoked = {},
            permissionState = rememberMultiplePermissionsState(
                permissions = listOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            )
        )
    Log.d(
        "RegisterScreen",
        "Longitude: ${uiState.longitude}, Latitude: ${uiState.latitude}"
    )
}

//@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO, locale = "en")
//@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES, locale = "ar")
//@Composable
//fun RegisterScreenPreview() {
//    KhomasiTheme {
//        RegisterScreen(
//            onLoginClick = {},
//            route = ""
//        )
//    }
//}