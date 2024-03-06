package com.company.khomasi.presentation.resetPassword

import android.content.res.Configuration
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.PasswordStrengthMeter
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.CheckInputValidation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@Composable
fun ResetPassword2(
    recreateViewModel: ResetPasswordViewModel ,
    onBackToLogin: () -> Unit
) {

    val recreateUiState = recreateViewModel.recreateUiState.collectAsState().value
    val verificationRes = recreateViewModel.verificationRes.collectAsState().value
    val recoverRes = recreateViewModel.recoverResponse.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager: FocusManager = LocalFocusManager.current

    var validatingSwitch by remember {
        mutableStateOf(false)
    }
    val code = when (verificationRes) {
        is DataState.Loading -> "Loading..."
        is DataState.Success -> verificationRes.data.code.toString()
        is DataState.Error -> verificationRes.message
        is DataState.Empty -> "Empty"
    }

    when (recoverRes) {
        is DataState.Loading -> {
            Loading()
        }

        is DataState.Success -> {
            onBackToLogin()
        }

        is DataState.Error -> {

        }

        is DataState.Empty -> {}
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
                .padding(bottom = 8.dp),
            //.clickable { recreateViewModel.onClickButtonScreen1() },
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
                    localFocusManager.moveFocus(FocusDirection.Down)
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
                    localFocusManager.moveFocus(FocusDirection.Down)
                }
            ),
            keyBoardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
        )

        PasswordStrengthMeter(
            password = recreateUiState.newPassword,
            enable = recreateUiState.newPassword.isNotEmpty()
        )

        Text(
            if (validatingSwitch && !CheckInputValidation.isPasswordValid(recreateUiState.newPassword)){
                stringResource(R.string.invalid_pass_message)
                }
            else{
                stringResource(id = R.string.password_restrictions)

            },
            style = MaterialTheme.typography.labelSmall,
            color = if (validatingSwitch && !CheckInputValidation.isPasswordValid(recreateUiState.newPassword)) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(32.dp))

        MyTextField(
            value = recreateUiState.rewritingNewPassword,
            onValueChange = { recreateViewModel.onReTypingPassword(it) },
            label = R.string.retype_password,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            keyBoardType = KeyboardType.Password,
            isError = (recreateUiState.newPassword != recreateUiState.rewritingNewPassword
                    && recreateUiState.rewritingNewPassword.isNotEmpty())
        )
        if (recreateUiState.newPassword != recreateUiState.rewritingNewPassword
            && recreateUiState.rewritingNewPassword.isNotEmpty()){
            Text(
                text = stringResource(R.string.not_matched_passwords),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        MyButton(
            text = R.string.set_password,
            onClick = {
                validatingSwitch = true
                recreateViewModel.onButtonClickedScreen2()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            shape = MaterialTheme.shapes.medium,
        )

        MyTextButton(
            text = R.string.back_to_login,
            onClick = onBackToLogin,
        )
    }
}

//@Preview(name = "Night", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Preview(
//    name = "Light",
//    uiMode = Configuration.UI_MODE_NIGHT_NO,
//    locale = "ar",
//    showSystemUi = true
//)
////@Composable
////fun RecreateNewPasswordPreview() {
////    KhomasiTheme {
////        ResetPassword2 { }
////    }
////}
