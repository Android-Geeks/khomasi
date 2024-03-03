package com.company.khomasi.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.presentation.components.AuthSheet


@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onDoneClick: () -> Unit,
    backToLoginOrRegister: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
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
        sheetContent = {
            RegisterDataPage(
                viewModel = viewModel,
                onLoginClick = onLoginClick,
                onDoneClick = onDoneClick,
                backToLoginOrRegister = backToLoginOrRegister
            )
        }
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