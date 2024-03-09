package com.company.khomasi.presentation.login

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.presentation.components.connectionStates.LossConnection
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
    onLoginSuccess: () -> Unit,
    loginWithGmail: () -> Unit,
    privacyAndPolicy: () -> Unit,
    helpAndSupport: () -> Unit,
    isValidEmailAndPassword: (String, String) -> Boolean,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val loginStateValue = loginState.collectAsState().value
    val context : Context = LocalContext.current
    Box {
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
        when (loginStateValue) {
            is DataState.Loading -> {
                Loading()
            }

            is DataState.Success -> {
                onLoginSuccess()
            }

            is DataState.Error -> {
                Log.d("LoginDataPage", "Error: ${loginStateValue.message}")
                when (loginStateValue.message) {
                    "Invalid password" -> {
                        Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
                    }
                    "Invalid user email" -> {
                        Toast.makeText(context, "Invalid user email", Toast.LENGTH_SHORT).show()
                    }
                    "Network error" -> {
                        LossConnection {
                            login()
                        }
                    }
                    else -> {}
                }
            }

            is DataState.Empty -> {
                Log.d("LoginDataPage", "Empty")
            }
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
            onLoginSuccess = mockViewModel::onLoginSuccess,
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