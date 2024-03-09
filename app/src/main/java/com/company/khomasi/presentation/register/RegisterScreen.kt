package com.company.khomasi.presentation.register

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow


@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onDoneClick: () -> Unit,
    backToLoginOrRegister: () -> Unit,
    uiState: State<RegisterUiState>,
    registerState: StateFlow<DataState<UserRegisterResponse>>,
    onRegister: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    isValidNameAndPhoneNumber: (String, String, String) -> Boolean,
    isValidEmailAndPassword: (String, String) -> Boolean,
    onNextClick: () -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onBack : ()-> Unit,
) {
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
                    backToLoginOrRegister = backToLoginOrRegister,
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
                    onBack =onBack
                )
            }
        )
        when (registerState.collectAsState().value) {
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
}

@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO, locale = "en")
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES, locale = "ar")
@Composable
fun RegisterScreenPreview() {
    KhomasiTheme {
        val mockViewModel: MockRegisterViewModel = viewModel()
        RegisterScreen(
            onLoginClick = {},
            onDoneClick = {},
            backToLoginOrRegister = {},
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
            onBack = mockViewModel::onBack
        )
    }
}