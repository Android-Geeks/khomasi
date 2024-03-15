package com.company.khomasi.presentation.resetPassword

import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MessageResponse
import com.company.khomasi.domain.model.VerificationResponse
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.PasswordStrengthMeter
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.CheckInputValidation
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun ResetPassword(
    onCancelClick: () -> Unit,
    onBackToLogin: () -> Unit,
    uiState: State<ResetPasswordUiState>,
    verificationRes: StateFlow<DataState<VerificationResponse>>,
    recoverResponse: StateFlow<DataState<MessageResponse>>,
    onUserEmailChange: (String) -> Unit,
    onClickButtonScreen1: () -> Unit,
    onEnteringVerificationCode: (String) -> Unit,
    verifyVerificationCode: (String) -> Unit,
    onEnteringPassword: (String) -> Unit,
    onReTypingPassword: (String) -> Unit,
    onButtonClickedScreen2: () -> Unit,
    onBack: () -> Unit,
    onNextClick: () -> Unit,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    localFocusManager: FocusManager = LocalFocusManager.current
) {
    val resetUiState = uiState.value
    val verificationStatus = verificationRes.collectAsState().value
    val recoverStatus = recoverResponse.collectAsState().value
    var showLoading by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf("") }

    val isEmailError =
        resetUiState.validating1 && !CheckInputValidation.isEmailValid(resetUiState.userEmail)
    val isPasswordError =
        resetUiState.validating2 && !CheckInputValidation.isPasswordValid(resetUiState.newPassword)
    val isPasswordMatchError = resetUiState.newPassword != resetUiState.rewritingNewPassword
            && resetUiState.rewritingNewPassword.isNotEmpty()

    BackHandler {
        if (resetUiState.page == 2) {
            onBack()
        } else {
            onBackToLogin()
        }
    }

    LaunchedEffect(key1 = verificationStatus) {
        Log.d("VerificationStatus", "VerificationStatus: $verificationStatus")
        when (verificationStatus) {
            is DataState.Loading -> {
                showLoading = true
            }

            is DataState.Success -> {
                showLoading = false
                code = verificationStatus.data.code.toString()
                onNextClick()
            }

            is DataState.Error -> {
                showLoading = false
                code = verificationStatus.message
            }

            is DataState.Empty -> {}
        }
    }

    LaunchedEffect(key1 = recoverStatus) {
        Log.d("RecoverStatus", "RecoverStatus: $recoverStatus")
        when (recoverStatus) {
            is DataState.Loading -> {
                showLoading = true
            }

            is DataState.Success -> {
                onBackToLogin()
                showLoading = false
            }

            is DataState.Error -> {
                showLoading = false
            }

            is DataState.Empty -> {}
        }
    }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    LaunchedEffect(key1 = keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
    ) {
        when (resetUiState.page) {
            1 ->
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
                        onValueChange = { onUserEmailChange(it) },
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
                            onClickButtonScreen1()
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
                        onValueChange = { onEnteringVerificationCode(it) },
                        label = R.string.verification_code,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                verifyVerificationCode(code)
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
                        onValueChange = { onEnteringPassword(it) },
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
                        onValueChange = { onReTypingPassword(it) },
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
                            onButtonClickedScreen2()
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
        if (showLoading) {
            Loading()
        }
}

@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ResetPassWordPreview() {
    KhomasiTheme {
        val mockResetPasswordViewModel: MockResetPasswordViewModel = viewModel()
        ResetPassword(
            onCancelClick = { },
            onBackToLogin = { },
            uiState = mockResetPasswordViewModel.resetUiState.collectAsState(),
            verificationRes = mockResetPasswordViewModel.verificationRes,
            recoverResponse = mockResetPasswordViewModel.recoverResponse,
            onUserEmailChange = mockResetPasswordViewModel::onUserEmailChange,
            onClickButtonScreen1 = { },
            onEnteringVerificationCode = mockResetPasswordViewModel::onEnteringVerificationCode,
            verifyVerificationCode = mockResetPasswordViewModel::verifyVerificationCode,
            onEnteringPassword = mockResetPasswordViewModel::onEnteringPassword,
            onReTypingPassword = mockResetPasswordViewModel::onReTypingPassword,
            onButtonClickedScreen2 = { },
            onBack = { },
            onNextClick = { })
    }

}