package com.company.khomasi.presentation.ui.screens.otpScreen


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(
    modifier: Modifier = Modifier,
    otpViewModel: OtpViewModel = viewModel()
) {
    //val otpUiState by otpViewModel.uiState.collectAsState()

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = stringResource(id = R.string.Your_code_has_arrived_in_your_email),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = modifier.height(84.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.enter_verification_email),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = modifier.weight(0.9f))
                Text(
                   text = "zeyad@gmail.com",
                    //text= otpUiState.email,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                    textDecoration = TextDecoration.Underline
                )
            }
            SmsCodeView(
                smsCodeLength = 5,
                textFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                ),
                textStyle = MaterialTheme.typography.displayLarge,
                smsFulled = otpViewModel::updateSmsCode
            )

            Spacer(modifier = modifier.height(24.dp))

            MyButton(
                text = R.string.confirm,
                onClick = {
                    otpViewModel.logIn()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 10.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
            )
            Spacer(modifier = modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ) {

                Text(
                    text = stringResource(id = R.string.did_not_receive_the_code),
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = stringResource(id = R.string.resend_code),
                    modifier = modifier
                        .clickable {
                            otpViewModel.resendCode()
                        },
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall

                )

            }

            Spacer(modifier = modifier.height(8.dp))

            var time by remember { mutableStateOf(59) }
            LaunchedEffect(Unit) {
                while (time > 0) {
                    delay(1000)
                    time--
                }
            }
            val minutes = String.format("%02d", time / 60)
            val seconds = String.format("%02d", time % 60)
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){

            Text(
                text = stringResource(id = R.string.resend_code_after)+"  ",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodySmall

            )
            Text(
                text = "$minutes:$seconds",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodySmall
            )

        }
    }


    }
}

@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LogInPreview() {
    KhomasiTheme {
        OtpScreen()

    }
}