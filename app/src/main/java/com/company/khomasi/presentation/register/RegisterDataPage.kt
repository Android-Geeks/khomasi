package com.company.khomasi.presentation.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.CheckInputValidation
import com.company.khomasi.presentation.components.PasswordStrengthMeter

@Composable
fun RegisterDataPage(
    viewModel: RegisterViewModel,
    onLoginClick: () -> Unit,
    backToLoginOrRegister: () -> Unit,
    localFocusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val userState = viewModel.uiState.value

    BackHandler {
        if (userState.page == 2) {
            viewModel.onBack()
        } else {
            backToLoginOrRegister()
        }
    }

    val keyboardActions = KeyboardActions(
        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
        onDone = {
            keyboardController?.hide()
        }
    )
    Column(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Adjust the spacing between items
    ) {
        when (userState.page) {
            1 -> {
                Text(
                    text = stringResource(id = R.string.please_confirm_your_information),
                    style = MaterialTheme.typography.displayMedium,
                    color = if (isDark) darkText else lightText,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                MyTextField(
                    value = userState.firstName,
                    onValueChange = viewModel::onFirstNameChange,
                    label = R.string.first_name,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Text,
                    keyboardActions = keyboardActions,
                    isError = (userState.validating1 && !(CheckInputValidation.isLastNameValid(userState.firstName)))
                )
                MyTextField(
                    value = userState.lastName,
                    onValueChange = viewModel::onLastNameChange,
                    label = R.string.last_name,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Text,
                    keyboardActions = keyboardActions,
                    isError = (userState.validating1 && !(CheckInputValidation.isLastNameValid(userState.lastName)))
                )
                if (userState.validating1
                    && ((!CheckInputValidation.isLastNameValid(userState.lastName))
                            || (!CheckInputValidation.isFirstNameValid(userState.firstName)))
                    ){
                    Text(
                        text = stringResource( R.string.invalid_name_message),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                MyTextField(
                    value = userState.phoneNumber,
                    onValueChange = viewModel::onPhoneNumberChange,
                    label = R.string.phone_number,
                    imeAction = ImeAction.Done,
                    keyBoardType = KeyboardType.Phone,
                    keyboardActions = keyboardActions,
                    isError = (userState.validating1 && !CheckInputValidation.isPhoneNumberValid(userState.phoneNumber))
                )
                if (userState.validating1 && !CheckInputValidation.isPhoneNumberValid(userState.phoneNumber)){
                    Text(
                        text = stringResource( R.string.invalid_phone_number_message),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(84.dp))
                MyButton(
                    text = R.string.next,
                    onClick = {
                        if (viewModel.isValidNameAndPhoneNumber(
                                userState.firstName,
                                userState.lastName,
                                userState.phoneNumber
                            )
                        ) {
                            viewModel.onNextClick()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            2 -> {
                Text(
                    text = stringResource(id = R.string.you_are_almost_there),
                    style = MaterialTheme.typography.displayMedium,
                    color = if (isDark) darkText else lightText
                )
                MyTextField(
                    value = userState.email,
                    onValueChange = viewModel::onEmailChange,
                    label = R.string.email,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Email,
                    keyboardActions = keyboardActions,
                    isError = (userState.validating2 && !CheckInputValidation.isEmailValid(userState.email))
                )
                if (userState.validating2 && !CheckInputValidation.isEmailValid(userState.email)){
                    Text(
                        text = stringResource(R.string.invalid_email_message),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                MyTextField(
                    value = userState.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = R.string.password,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Password,
                    keyboardActions = keyboardActions,
                    isError = (userState.validating2 && !CheckInputValidation.isPasswordValid(userState.password))
                )
                PasswordStrengthMeter(
                    password = userState.password,
                    enable = userState.password.isNotEmpty()
                )

                Text(
                    if (userState.validating2 && !CheckInputValidation.isPasswordValid(userState.password)){
                        stringResource(R.string.invalid_pass_message)
                    }
                    else{
                        stringResource(id = R.string.password_restrictions)

                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = if (userState.validating2 && !CheckInputValidation.isPasswordValid(userState.password)) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                )
                MyTextField(
                    value = userState.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    label = R.string.confirm_password,
                    imeAction = ImeAction.Done,
                    keyBoardType = KeyboardType.Password,
                    keyboardActions = keyboardActions,
                    isError =  (userState.password != userState.confirmPassword
                            && userState.confirmPassword.isNotEmpty())
                )
                if (userState.password != userState.confirmPassword
                    && userState.confirmPassword.isNotEmpty()){
                    Text(
                        text = stringResource(R.string.not_matched_passwords),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(84.dp))
                MyButton(
                    text = R.string.create_account,
                    onClick = {
                        if (viewModel.isValidEmailAndPassword(
                                userState.email,
                                userState.password
                            )
                        ) {
                            viewModel.onRegister()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.already_have_an_account),
                style = MaterialTheme.typography.bodySmall,
                color = if (isDark) darkText else lightText
            )
            MyTextButton(
                text = R.string.login,
                isUnderlined = false,
                onClick = onLoginClick
            )
        }
    }
}

//@Preview
//@Composable
//fun RegisterDataPagePreview() {
//    KhomasiTheme {
//        RegisterDataPage(
//            onNextClick = {  },
//            onLoginClick = {  },
//            route = "",
//            onDoneClick = { },
//        )
//    }
//}