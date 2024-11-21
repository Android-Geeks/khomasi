package com.company.rentafield.presentation.screens.otp


import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.components.AuthSheet
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.connectstates.Loading
import com.company.rentafield.presentation.theme.RentafieldTheme

@Composable
fun OtpScreenRoute(
    modifier: Modifier = Modifier,
    onEmailConfirmed: () -> Unit,
    viewModel: OtpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val otpStatus by viewModel.otpState.collectAsStateWithLifecycle()
    val confirmEmailStatus by viewModel.confirmEmailState.collectAsStateWithLifecycle()

    var showLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getRegisterOtp()
    }

    LaunchedEffect(key1 = otpStatus) {
        Log.d("OtpScreen", "otpStatus: $otpStatus")
        when (otpStatus) {
            is DataState.Loading -> {
                showLoading = true
            }

            is DataState.Success -> {
                showLoading = false
            }

            is DataState.Error -> {
                showLoading = false
            }

            is DataState.Empty -> {
                // Empty
            }
        }
    }
    LaunchedEffect(key1 = confirmEmailStatus) {
        Log.d("OtpScreen", "confirmEmailStatus: $confirmEmailStatus")
        when (confirmEmailStatus) {
            is DataState.Loading -> {
                showLoading = true
            }

            is DataState.Success -> {
                showLoading = false
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_LONG).show()
                onEmailConfirmed()
            }

            is DataState.Error -> {
                showLoading = false
            }

            is DataState.Empty -> {
                // Empty
            }
        }
    }

    OtpScreen(
        onEmailConfirmed = onEmailConfirmed,
        uiState = uiState,
        confirmEmailState = confirmEmailStatus,
        otpState = otpStatus,
        getRegisterOtp = viewModel::getRegisterOtp,
        updateSmsCode = viewModel::updateSmsCode,
        resendCode = viewModel::resendCode,
        confirmEmail = viewModel::confirmEmail,
        startTimer = viewModel::startTimer,
        resetTimer = viewModel::resetTimer,
        showLoading = showLoading,
        modifier = modifier
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun OtpScreen(
    uiState: OtpUiState,
    confirmEmailState: DataState<com.company.rentafield.domain.models.MessageResponse>,
    otpState: DataState<com.company.rentafield.domain.models.auth.VerificationResponse>,
    updateSmsCode: (String) -> Unit,
    resendCode: () -> Unit,
    onEmailConfirmed: () -> Unit,
    confirmEmail: () -> Unit,
    startTimer: (Int) -> Unit,
    resetTimer: (Int) -> Unit,
    getRegisterOtp: () -> Unit,
    showLoading: Boolean,
    modifier: Modifier = Modifier,
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
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .imePadding() // padding for the bottom for the IME

            ) {
                Text(
                    text = stringResource(id = R.string.Your_code_has_arrived_in_your_email),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.enter_verification_email),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = uiState.email,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))

                CodeTextField(
                    value = uiState.code,
                    length = 5,
                    onValueChange = updateSmsCode,
                    textStyle = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                MyButton(
                    text = R.string.confirm,
                    onClick = {
                        if (uiState.code.length == 5)
                            confirmEmail()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))

                LaunchedEffect(key1 = uiState) {
                    startTimer(uiState.timer)
                }

                val minutes = String.format("%02d", uiState.timer / 60)
                val seconds = String.format("%02d", uiState.timer % 60)

                if (uiState.timer == 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.did_not_receive_the_code),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(
                            text = stringResource(id = R.string.resend_code),
                            modifier = Modifier
                                .clickable {
                                    resetTimer(59)
                                    resendCode()
                                },
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall

                        )
                    }
                }

                if (uiState.timer > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.resend_code_after),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodySmall

                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(
                            text = "$minutes:$seconds",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        if (showLoading) {
            Loading()
        }
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
fun OtpPreview() {
    RentafieldTheme {
        OtpScreen(
            onEmailConfirmed = {},
            uiState = OtpUiState(),
            confirmEmailState = DataState.Empty,
            otpState = DataState.Empty,
            updateSmsCode = {},
            resendCode = {},
            confirmEmail = {},
            startTimer = {},
            resetTimer = {},
            getRegisterOtp = {},
            showLoading = false
        )
    }
}