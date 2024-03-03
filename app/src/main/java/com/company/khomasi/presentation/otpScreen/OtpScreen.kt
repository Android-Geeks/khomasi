package com.company.khomasi.presentation.otpScreen


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(
    modifier: Modifier = Modifier,
    otpViewModel: OtpViewModel = hiltViewModel()
) {
    //val otpUiState by otpViewModel.uiState.collectAsState()
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
                text = "zeyad@gmail.com",
                //text= otpUiState.email,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.padding(2.dp))
        SmsCodeView(
            smsCodeLength = 5,
            // Need to add colors here
            textFieldColors = TextFieldDefaults.colors(),
            textStyle = MaterialTheme.typography.displayLarge,
            smsFulled = otpViewModel::updateSmsCode
        )

        Spacer(modifier = Modifier.height(24.dp))

        MyButton(
            text = R.string.confirm,
            onClick = {
                otpViewModel.login()
            },
            contentPadding = PaddingValues(horizontal = 34.dp, vertical = 9.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 10.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        )
        Spacer(modifier = Modifier.height(32.dp))

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
                        otpViewModel.resendCode()
                    },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall

            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        var time by remember { mutableStateOf(59) }
        LaunchedEffect(Unit) {
            while (time > 0) {
                delay(1000)
                time--
            }
        }
        val minutes = String.format("%02d", time / 60)
        val seconds = String.format("%02d", time % 60)
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


@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OtpPreview() {
    KhomasiTheme {
        OtpScreen()
    }
}