package com.company.rentafield.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.utils.ThemePreviews
import com.company.rentafield.utils.rememberFlowWithLifecycle

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigate: (LoginReducer.Effect) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    val context = LocalContext.current

    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is LoginReducer.Effect.Error -> {
                    when (action) {
                        LoginReducer.Effect.Error.EmailNotConfirmed -> {
                            viewModel.verifyEmail()
                            onNavigate(LoginReducer.Effect.Error.EmailNotConfirmed)
                        }

                        else -> Unit
                    }
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    onNavigate(action) // Handle other effects via the higher-order function
                }
            }
        }
    }

    LoginScreenContent(
        state = state,
        login = viewModel::login,
        modifier = Modifier.verticalScroll(rememberScrollState()),
        sendEvent = viewModel::sendEventForEffect
    )

}


@ThemePreviews
@Composable
fun LoginPreview() {
    RentafieldTheme {
        LoginScreenContent(
            state = LoginReducer.initial(),
            login = {},
            sendEvent = {}
        )
    }
}