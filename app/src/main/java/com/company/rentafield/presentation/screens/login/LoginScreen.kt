package com.company.rentafield.presentation.screens.login

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.auth.UserLoginResponse
import com.company.rentafield.presentation.components.AuthSheet
import com.company.rentafield.presentation.components.connectionStates.Loading
import com.company.rentafield.theme.RentafieldTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    uiState: StateFlow<LoginUiState>,
    loginState: StateFlow<DataState<UserLoginResponse>>,
    updatePassword: (String) -> Unit,
    updateEmail: (String) -> Unit,
    login: () -> Unit,
    onLoginSuccess: (DataState.Success<UserLoginResponse>) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    verifyEmail: () -> Unit,
    loginWithGmail: () -> Unit,
    privacyAndPolicy: () -> Unit,
    helpAndSupport: () -> Unit,
    isValidEmailAndPassword: (String, String) -> Boolean,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val loginStatus by loginState.collectAsStateWithLifecycle()
    var showLoading by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    LaunchedEffect(key1 = loginStatus) {
        when (val status = loginStatus) {
            is DataState.Loading -> {
                showLoading = true
                keyboardController?.hide()
            }

            is DataState.Success -> {
                showLoading = false
                if (status.data.role == "User") {
                    onLoginSuccess(status)
                }
            }

            is DataState.Error -> {
                showLoading = false
                if (status.code == 404) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.user_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (status.code == 400 && status.message == "Invalid Password.") {
                    Toast.makeText(
                        context,
                        context.getString(R.string.invalid_email_or_password),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (status.code == 400 && status.message == "Email is not Confirmed.") {
                    verifyEmail()
                } else if (status.code == 0) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.login_only_message),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is DataState.Empty -> {}
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AuthSheet(
                modifier = modifier,
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
            ) {
                LoginDataPage(
                    isDark = isDark,
                    onRegisterClick = onRegisterClick,
                    onForgotPasswordClick = onForgotPasswordClick,
                    loginUiState = uiState,
                    updatePassword = updatePassword,
                    updateEmail = updateEmail,
                    login = login,
                    loginWithGmail = loginWithGmail,
                    privacyAndPolicy = privacyAndPolicy,
                    helpAndSupport = helpAndSupport,
                    isValidEmailAndPassword = isValidEmailAndPassword,
                    modifier = modifier.verticalScroll(rememberScrollState()),
                    keyboardController = keyboardController
                )
            }
            if (showLoading) {
                Loading()
            }
        }
    }
}


@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    RentafieldTheme {
        val mockViewModel = LoginMockViewModel()
        LoginScreen(
            uiState = mockViewModel.uiState,
            loginState = mockViewModel.loginState,
            updatePassword = mockViewModel::updatePassword,
            updateEmail = mockViewModel::updateEmail,
            login = mockViewModel::login,
            onRegisterClick = {},
            onForgotPasswordClick = {},
            verifyEmail = {},
            loginWithGmail = mockViewModel::loginWithGmail,
            privacyAndPolicy = mockViewModel::privacyAndPolicy,
            helpAndSupport = mockViewModel::helpAndSupport,
            isValidEmailAndPassword = { _, _ -> true },
            onLoginSuccess = {}
        )
    }
}