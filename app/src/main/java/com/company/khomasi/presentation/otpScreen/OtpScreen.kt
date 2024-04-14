package com.company.khomasi.presentation.otpScreen


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MessageResponse
import com.company.khomasi.domain.model.VerificationResponse
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun OtpScreen(
    onEmailConfirmed: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: State<OtpUiState>,
    confirmEmailState: StateFlow<DataState<MessageResponse>>,
    otpState: StateFlow<DataState<VerificationResponse>>,
    updateSmsCode: (String) -> Unit,
    resendCode: () -> Unit,
    confirmEmail: () -> Unit,
    startTimer: (Int) -> Unit,
    resetTimer: (Int) -> Unit,
    getRegisterOtp: () -> Unit,
) {
    val otpUiState = uiState.value
    val otpStatus = otpState.collectAsState().value
    val confirmEmailStatus = confirmEmailState.collectAsState().value
    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        getRegisterOtp()
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
                        text = otpUiState.email,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))

                CodeTextField(
                    value = otpUiState.code,
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
                        if (otpUiState.code.length == 5)
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

                LaunchedEffect(key1 = otpUiState) {
                    startTimer(otpUiState.timer)
                }

                val minutes = String.format("%02d", otpUiState.timer / 60)
                val seconds = String.format("%02d", otpUiState.timer % 60)

                if (otpUiState.timer == 0) {
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

                if (otpUiState.timer > 0) {
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

@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OtpPreview() {
    KhomasiTheme {
        val mockOtpViewModel: MockOtpViewModel = viewModel()
        OtpScreen(
            onEmailConfirmed = {},
            uiState = mockOtpViewModel.uiState.collectAsState(),
            confirmEmailState = mockOtpViewModel.confirmEmailState,
            otpState = mockOtpViewModel.otpState,
            updateSmsCode = mockOtpViewModel::updateSmsCode,
            resendCode = mockOtpViewModel::resendCode,
            confirmEmail = mockOtpViewModel::confirmEmail,
            startTimer = mockOtpViewModel::startTimer,
            resetTimer = mockOtpViewModel::resetTimer,
            getRegisterOtp = {}
        )
    }
}