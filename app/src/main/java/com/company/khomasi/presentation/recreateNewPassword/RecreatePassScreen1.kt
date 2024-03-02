package com.company.khomasi.presentation.recreateNewPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.lightText
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.VerificationResponse
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
@Composable
fun RecreatePassScreen1(
    recreateNewPassViewModel: RecreateNewPassViewModel = hiltViewModel()
) {
    val recreateNewPassUiState by recreateNewPassViewModel.recreateUiState.collectAsState()
    val verificationRes by recreateNewPassViewModel.verificationRes.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val res = when (verificationRes) {
        is DataState.Loading -> "Loading..."
        is DataState.Success -> (verificationRes as DataState.Success<VerificationResponse>).data.code.toString()
        is DataState.Error -> (verificationRes as DataState.Error).message
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
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
            color = lightText
        )
        Text(
            text = stringResource(id = R.string.enter_email_to_reset_password),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(54.dp))

        MyTextField(
            value = recreateNewPassUiState.userEmail,
            onValueChange = { recreateNewPassViewModel.onUserEmailChange(it) },
            label = R.string.email,
            onImeAction = { keyboardController?.hide() },
            keyBoardType = KeyboardType.Email,
        )
        MyButton(
            text = R.string.set_password,
            onClick = {
                recreateNewPassViewModel.onClickButtonScreen1()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 32.dp)
        )

        MyTextButton(
            text = R.string.cancel,
            onClick = {},
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun ResetPasswordScreenPreview() {
    KhomasiTheme{
        RecreatePassScreen1()
    }
}