package com.company.khomasi.presentation.login

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
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
    ) {
        LoginDataPage(
            isDark = isDark,
            loginViewModel = loginViewModel,
            onRegisterClick = onRegisterClick,
            onForgotPasswordClick = onForgotPasswordClick,
            onSuccessLogin = loginViewModel::onLoginSuccess,
            modifier = modifier
        )
    }
}


@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    KhomasiTheme {
        LoginScreen({}, {})
    }
}