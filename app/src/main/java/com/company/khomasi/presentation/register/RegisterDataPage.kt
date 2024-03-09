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
import androidx.compose.runtime.State
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
import com.company.khomasi.presentation.components.PasswordStrengthMeter
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.CheckInputValidation

@Composable
fun RegisterDataPage(
    onLoginClick: () -> Unit,
    backToLoginOrRegister: () -> Unit,
    localFocusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    isDark: Boolean = isSystemInDarkTheme(),
    uiState: State<RegisterUiState>,
    onRegister: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    isValidNameAndPhoneNumber: (String, String, String) -> Boolean,
    isValidEmailAndPassword: (String, String) -> Boolean,
    onNextClick: () -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onBack: () -> Unit,


) {
    val userState = uiState.value

    val isErrorFirstName =
        userState.validating1 && !(CheckInputValidation.isFirstNameValid(userState.firstName))
    val isErrorLastName =
        userState.validating1 && !(CheckInputValidation.isLastNameValid(userState.lastName))
    val isErrorPhoneNumber =
        userState.validating1 && !CheckInputValidation.isPhoneNumberValid(userState.phoneNumber)
    val isErrorEmail = userState.validating2 && !CheckInputValidation.isEmailValid(userState.email)
    val isErrorPassword =
        userState.validating2 && !CheckInputValidation.isPasswordValid(userState.password)
    val isErrorConfirmPassword = userState.password != userState.confirmPassword
            && userState.confirmPassword.isNotEmpty()

    BackHandler {
        if (userState.page == 2) {
            onBack()
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
                    onValueChange = onFirstNameChange,
                    label = R.string.first_name,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Text,
                    keyboardActions = keyboardActions,
                    isError = isErrorFirstName,
                    supportingText = {
                        if (isErrorFirstName)
                            Text(
                                text = stringResource(R.string.invalid_name_message),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                    }
                )
                MyTextField(
                    value = userState.lastName,
                    onValueChange = onLastNameChange,
                    label = R.string.last_name,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Text,
                    keyboardActions = keyboardActions,
                    isError = isErrorLastName,
                    supportingText = {
                        if (isErrorLastName)
                            Text(
                                text = stringResource(R.string.invalid_name_message),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                    }
                )

                MyTextField(
                    value = userState.phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    label = R.string.phone_number,
                    imeAction = ImeAction.Done,
                    keyBoardType = KeyboardType.Phone,
                    keyboardActions = keyboardActions,
                    isError = isErrorPhoneNumber,
                    supportingText = {
                        if (isErrorPhoneNumber)
                            Text(
                                text = stringResource(R.string.invalid_phone_number_message),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                    }
                )

                Spacer(modifier = Modifier.height(84.dp))
                MyButton(
                    text = R.string.next,
                    onClick = {
                        if (isValidNameAndPhoneNumber(
                                userState.firstName,
                                userState.lastName,
                                userState.phoneNumber
                            )
                        ) {
                            onNextClick()
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
                    onValueChange = onEmailChange,
                    label = R.string.email,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Email,
                    keyboardActions = keyboardActions,
                    isError = isErrorEmail,
                    supportingText = {
                        if (isErrorEmail)
                            Text(
                                text = stringResource(R.string.invalid_email_message),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                    }
                )

                MyTextField(
                    value = userState.password,
                    onValueChange = onPasswordChange,
                    label = R.string.password,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Password,
                    keyboardActions = keyboardActions,
                    isError = isErrorPassword,
                    supportingText = {
                        Column {
                            PasswordStrengthMeter(
                                password = userState.password,
                                enable = userState.password.isNotEmpty()
                            )
                            Text(
                                text =
                                if (isErrorPassword) {
                                    stringResource(id = R.string.invalid_pass_message)
                                } else {
                                    stringResource(id = R.string.password_restrictions)
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isErrorPassword)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.outline
                            )

                        }
                    }
                )

                MyTextField(
                    value = userState.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    label = R.string.confirm_password,
                    imeAction = ImeAction.Done,
                    keyBoardType = KeyboardType.Password,
                    keyboardActions = keyboardActions,
                    isError = isErrorConfirmPassword,
                    supportingText = {
                        if (isErrorConfirmPassword) {
                            Text(
                                text = stringResource(R.string.not_matched_passwords),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(84.dp))
                MyButton(
                    text = R.string.create_account,
                    onClick = {
                        if (isValidEmailAndPassword(
                                userState.email,
                                userState.password
                            )
                        ) {
                            onRegister()
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

