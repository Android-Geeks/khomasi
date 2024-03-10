package com.company.khomasi.presentation.register

import android.content.Context
import android.util.Log
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
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.presentation.components.getUserLocation

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onDoneClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState = viewModel.uiState.collectAsState().value
    if (uiState.longitude == 0.0) {
        viewModel.updateLocation(getUserLocation(context = context))
    }
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
    if (uiState.longitude != 0.0 && uiState.latitude != 0.0) {
        Log.d(
            "RegisterScreen",
            "Longitude: ${uiState.longitude}, Latitude: ${uiState.latitude}"
        )
    }
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