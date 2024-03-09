package com.company.khomasi.presentation.resetPassword

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.PasswordStrengthMeter
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.presentation.components.connectionStates.LossConnection
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.CheckInputValidation

@Composable
fun ResetPasswordScreen(
    resetViewModel: ResetPasswordViewModel,
    onCancelClick: () -> Unit,
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    localFocusManager: FocusManager = LocalFocusManager.current
) {
    val resetUiState = resetViewModel.resetUiState.collectAsState().value
    val verificationRes = resetViewModel.verificationRes.collectAsState().value
    val recoverRes = resetViewModel.recoverResponse.collectAsState().value

    val isEmailError =
        resetUiState.validating1 && !CheckInputValidation.isEmailValid(resetUiState.userEmail)
    val isPasswordError =
        resetUiState.validating2 && !CheckInputValidation.isPasswordValid(resetUiState.newPassword)
    val isPasswordMatchError = resetUiState.newPassword != resetUiState.rewritingNewPassword
            && resetUiState.rewritingNewPassword.isNotEmpty()

    BackHandler {
        if (resetUiState.page == 2) {
            resetViewModel.onBack()
        } else {
            onBackToLogin()
        }
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
            LossConnection {
                resetViewModel.onClickButtonScreen2()
            }
        }

        is DataState.Empty -> {}
    }

    Column(modifier = modifier.fillMaxSize()) {

        when (resetUiState.page) {
            1 ->
                Box {
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
                            value = resetUiState.userEmail,
                            onValueChange = { resetViewModel.onUserEmailChange(it) },
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
                                resetViewModel.onClickButtonScreen1()
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
                    when (verificationRes) {
                        is DataState.Loading -> {
                            Loading()
                        }

                        is DataState.Success -> {
                            resetViewModel.onNextClick()
                            Log.d("ResetPassword1", "${verificationRes.data.code}")
                        }

                        is DataState.Error -> {
                            Log.d("ResetPassword1", verificationRes.message)
                            LossConnection {
                                resetViewModel.onClickButtonScreen1()
                            }
                        }

                        is DataState.Empty -> {
                            Log.d("ResetPassword1", "Empty")
                        }
                    }
                }

            2 ->
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
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = stringResource(id = R.string.create_new_password),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(56.dp))

                    MyTextField(
                        value = resetUiState.enteredVerificationCode.take(5),
                        onValueChange = { resetViewModel.onEnteringVerificationCode(it) },
                        label = R.string.verification_code,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                resetViewModel.verifyVerificationCode(code)
                                localFocusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        imeAction = ImeAction.Next,
                        keyBoardType = KeyboardType.Number,
                        isError = !(resetUiState.isCodeTrue)
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    MyTextField(
                        value = resetUiState.newPassword,
                        onValueChange = { resetViewModel.onEnteringPassword(it) },
                        label = R.string.new_password,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                localFocusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        keyBoardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                        isError = isPasswordError,
                        supportingText = {
                            Column {
                                PasswordStrengthMeter(
                                    password = resetUiState.newPassword,
                                    enable = resetUiState.newPassword.isNotEmpty()
                                )
                                Text(
                                    text = if (isPasswordError) {
                                        stringResource(id = R.string.invalid_pass_message)
                                    } else {
                                        stringResource(id = R.string.password_restrictions)
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isPasswordError)
                                        MaterialTheme.colorScheme.error
                                    else
                                        MaterialTheme.colorScheme.outline
                                )
                            }

                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    MyTextField(
                        value = resetUiState.rewritingNewPassword,
                        onValueChange = { resetViewModel.onReTypingPassword(it) },
                        label = R.string.retype_password,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        keyBoardType = KeyboardType.Password,
                        isError = isPasswordMatchError,
                        supportingText = {
                            if (isPasswordMatchError) {
                                Text(
                                    text = stringResource(R.string.not_matched_passwords),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    MyButton(
                        text = R.string.set_password,
                        onClick = {
                            resetViewModel.onClickButtonScreen2()
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
    }
}