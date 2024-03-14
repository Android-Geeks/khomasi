package com.company.khomasi.presentation.login

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    uiState: State<LoginUiState>,
    loginState: StateFlow<DataState<UserLoginResponse>>,
    updatePassword: (String) -> Unit,
    updateEmail: (String) -> Unit,
    login: () -> Unit,
    loginWithGmail: () -> Unit,
    privacyAndPolicy: () -> Unit,
    helpAndSupport: () -> Unit,
    isValidEmailAndPassword: (String, String) -> Boolean,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val loginStatus = loginState.collectAsState().value
    var showLoading by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = loginStatus) {
        when (loginStatus) {
            is DataState.Loading -> {
                showLoading = true
            }

            is DataState.Success -> {
                showLoading = false
                Log.d("LoginScreen", "LoginScreen: ${loginStatus.data}")
            }

            is DataState.Error -> {
                showLoading = false
                Log.d("LoginScreen", "LoginScreen: ${loginStatus.message}")
            }

            is DataState.Empty -> {
                Log.d("LoginScreen", "LoginScreen: Empty")
            }
        }
    }
    Box {
        if (showLoading) {
            Loading()
        }
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
                isValidEmailAndPassword = isValidEmailAndPassword
            )
        }
    }

}


@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    KhomasiTheme {
        val mockViewModel = MockLoginViewModel()
        LoginScreen(
            updatePassword = mockViewModel::updatePassword,
            updateEmail = mockViewModel::updateEmail,
            login = mockViewModel::login,
            loginWithGmail = mockViewModel::loginWithGmail,
            privacyAndPolicy = mockViewModel::privacyAndPolicy,
            helpAndSupport = mockViewModel::helpAndSupport,
            isValidEmailAndPassword = mockViewModel::isValidEmailAndPassword,
            uiState = mockViewModel.uiState,
            loginState = mockViewModel.loginState,
            onRegisterClick = {},
            onForgotPasswordClick = {}
        )
    }
}