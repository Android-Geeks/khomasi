package com.company.khomasi.presentation.resetPassword

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.VerificationResponse
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.PasswordStrengthMeter
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkHint
import com.company.khomasi.theme.lightHint

@Composable
fun RecreatePassScreen2(
    recreateViewModel: ResetPasswordViewModel = hiltViewModel()
) {

    val recreateUiState by recreateViewModel.recreateUiState.collectAsState()
    val verificationRes by recreateViewModel.verificationRes.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val code = when (verificationRes) {
        is DataState.Loading -> "Loading..."
        is DataState.Success -> (verificationRes as DataState.Success<VerificationResponse>).data.code.toString()
        is DataState.Error -> (verificationRes as DataState.Error).message
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.reset_password),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clickable { recreateViewModel.onClickButtonScreen1() },
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = stringResource(id = R.string.create_new_password),
            style = MaterialTheme.typography.bodyMedium
        )
        /*        Text(
            text = code,
            style = MaterialTheme.typography.bodyMedium
        )*/
        Spacer(modifier = Modifier.height(56.dp))

        MyTextField(
            value = recreateUiState.enteredVerificationCode.take(5),
            onValueChange = { recreateViewModel.onEnteringVerificationCode(it) },
            label = R.string.verification_code,
            keyboardActions = KeyboardActions(
                onNext = {
                    recreateViewModel.verifyVerificationCode(code)
                    keyboardController?.hide()
                }
            ),
            imeAction = ImeAction.Next,
            keyBoardType = KeyboardType.Number,
            isError = !(recreateUiState.isCodeTrue)
        )

        Spacer(modifier = Modifier.height(32.dp))

        MyTextField(
            value = recreateUiState.newPassword,
            onValueChange = { recreateViewModel.onEnteringPassword(it) },
            label = R.string.new_password,
            keyboardActions = KeyboardActions(
                onNext = {
                    keyboardController?.hide()
                }
            ),
            keyBoardType = KeyboardType.Password
        )

        PasswordStrengthMeter(
            password = recreateUiState.newPassword,
            enable = recreateUiState.newPassword.isNotEmpty()
        )

        Text(
            text = stringResource(id = R.string.password_restrictions),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSystemInDarkTheme()) darkHint else lightHint
        )
        Spacer(modifier = Modifier.height(32.dp))

        MyTextField(
            value = recreateUiState.rewritingNewPassword,
            onValueChange = { recreateViewModel.onReTypingPassword(it) },
            label = R.string.retype_password,
            keyboardActions = KeyboardActions(
                onDone = {
                    recreateViewModel.checkPasswordMatching()
                    keyboardController?.hide()
                }
            ),
            keyBoardType = KeyboardType.Password,
            isError = !(recreateUiState.isTwoPassEquals)
        )

        Spacer(modifier = Modifier.height(32.dp))

        MyButton(
            text = R.string.set_password,
            onClick = {
                recreateViewModel.onButtonClickedScreen2()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            shape = MaterialTheme.shapes.medium,
            buttonEnable = recreateViewModel.checkValidation()
        )

        MyTextButton(
            text = R.string.back_to_login,
            onClick = {},
        )
    }
}

@Preview(name = "Night", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(
    name = "Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ar",
    showSystemUi = true
)
@Composable
fun RecreateNewPasswordPreview() {
    KhomasiTheme {
        RecreatePassScreen2()
    }
}
