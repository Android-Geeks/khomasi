package com.company.khomasi.presentation.register

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.LatandLong
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.presentation.components.getUserLocation
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow


@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onDoneClick: () -> Unit,
    uiState: State<RegisterUiState>,
    registerState: StateFlow<DataState<UserRegisterResponse>>,
    onRegister: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    isValidNameAndPhoneNumber: (String, String, String) -> Boolean,
    isValidEmailAndPassword: (String, String) -> Boolean,
    updateLocation: (LatandLong) -> Unit,
    onNextClick: () -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onBack: () -> Unit,
    onBackFromStack: () -> Unit,
    context: Context = LocalContext.current
) {
    val registerStatus = registerState.collectAsState().value
    var showLoading by remember { mutableStateOf(false) }

    if (uiState.value.longitude == 0.0) {
        updateLocation(getUserLocation(context = context))
    }

    LaunchedEffect(key1 = registerStatus) {
        Log.d("RegisterStatus", "$registerStatus")
        when (registerStatus) {
            is DataState.Loading -> {
                showLoading = true
            }

            is DataState.Success -> {
                showLoading = false
                onDoneClick()
            }

            is DataState.Error -> {
                showLoading = false
            }

            is DataState.Empty -> {}
        }
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
                    onLoginClick = onLoginClick,
                    uiState = uiState,
                    onRegister = onRegister,
                    onFirstNameChange = onFirstNameChange,
                    isValidNameAndPhoneNumber = isValidNameAndPhoneNumber,
                    isValidEmailAndPassword = isValidEmailAndPassword,
                    onNextClick = onNextClick,
                    onLastNameChange = onLastNameChange,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onConfirmPasswordChange = onConfirmPasswordChange,
                    onPhoneNumberChange = onPhoneNumberChange,
                    onBack = onBack,
                    onBackFromStack = onBackFromStack
                )
            },
        )
        if (showLoading) {
            Loading()
        }
    }
}

@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO, locale = "en")
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES, locale = "ar")
@Composable
fun RegisterScreenPreview() {
    KhomasiTheme {
        val mockViewModel = MockRegisterViewModel()
        RegisterScreen(
            onLoginClick = {},
            onDoneClick = {},
            uiState = mockViewModel.uiState,
            registerState = mockViewModel.registerState,
            onRegister = mockViewModel::onRegister,
            onFirstNameChange = mockViewModel::onFirstNameChange,
            isValidNameAndPhoneNumber = mockViewModel::isValidNameAndPhoneNumber,
            isValidEmailAndPassword = mockViewModel::isValidEmailAndPassword,
            onNextClick = mockViewModel::onNextClick,
            onLastNameChange = mockViewModel::onLastNameChange,
            onEmailChange = mockViewModel::onEmailChange,
            onPasswordChange = mockViewModel::onPasswordChange,
            onConfirmPasswordChange = mockViewModel::onConfirmPasswordChange,
            onPhoneNumberChange = mockViewModel::onPhoneNumberChange,
            onBack = mockViewModel::onBack,
            updateLocation = mockViewModel::updateLocation,
            onBackFromStack = {}
        )
    }
}