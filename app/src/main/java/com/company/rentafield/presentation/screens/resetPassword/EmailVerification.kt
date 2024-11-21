package com.company.rentafield.presentation.screens.resetPassword

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyTextButton
import com.company.rentafield.presentation.components.MyTextField
import com.company.rentafield.presentation.components.connectstates.Loading
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.utils.CheckInputValidation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun EmailVerification(
    uiState: StateFlow<ResetPasswordUiState>,
    verificationRes: StateFlow<DataState<com.company.rentafield.domain.models.auth.VerificationResponse>>,
    onCorrectCodeChange: (String) -> Unit,
    onUserEmailChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onClickButtonScreen1: () -> Unit,
    onSetPasswordClick: () -> Unit
) {
    val resetUiState by uiState.collectAsStateWithLifecycle()
    val verificationStatus by verificationRes.collectAsState()
    var showLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val isEmailError =
        resetUiState.validating1 && !CheckInputValidation.isEmailValid(resetUiState.userEmail)

    LaunchedEffect(key1 = verificationStatus) {
        Log.d("VerificationStatus", "VerificationStatus: $verificationStatus")
        when (val state = verificationStatus) {
            is DataState.Loading -> {
                showLoading = true
                keyboardController?.hide()
            }

            is DataState.Success -> {
                showLoading = false
                Toast.makeText(context, R.string.verification_code_sent, Toast.LENGTH_SHORT).show()
                onCorrectCodeChange(state.data.code.toString())
                onSetPasswordClick()
            }

            is DataState.Error -> {
                showLoading = false
                when (state.code) {
                    0 -> {
                        Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT)
                            .show()
                    }

                    404 -> {
                        Toast.makeText(context, R.string.user_not_found, Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            is DataState.Empty -> {}
        }
    }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    LaunchedEffect(key1 = keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.protect_key),
                contentDescription = null,
                modifier = Modifier.size(width = (93.8).dp, (123.2).dp)
            )
            Text(
                text = stringResource(id = R.string.forgot_your_password),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 40.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(id = R.string.enter_email_to_reset_password),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(54.dp))

            MyTextField(
                value = resetUiState.userEmail,
                onValueChange = { onUserEmailChange(it) },
                label = R.string.email,
                keyBoardType = KeyboardType.Email,
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                isError = isEmailError,
                supportingText = {
                    if (isEmailError) {
                        Text(
                            text = stringResource(R.string.invalid_email_message),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            )

            MyButton(
                text = R.string.set_password,
                onClick = {
                    onClickButtonScreen1()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 32.dp)
            )

            MyTextButton(
                text = R.string.cancel,
                onClick = onCancelClick,
            )
        }
    }
    if (showLoading) {
        Loading()
    }
}

@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun EmailVerificationPreview() {
    RentafieldTheme {
        EmailVerification(
            uiState = MutableStateFlow(ResetPasswordUiState()),
            verificationRes = MutableStateFlow(DataState.Empty),
            onUserEmailChange = {},
            onCorrectCodeChange = {},
            onCancelClick = {},
            onClickButtonScreen1 = {},
            onSetPasswordClick = {},
        )
    }
}