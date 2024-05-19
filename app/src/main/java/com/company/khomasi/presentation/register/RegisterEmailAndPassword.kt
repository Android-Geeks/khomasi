package com.company.khomasi.presentation.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.PasswordStrengthMeter
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.CheckInputValidation
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegisterEmailAndPassword(
    uiState: StateFlow<RegisterUiState>,
    registerState: StateFlow<DataState<UserRegisterResponse>>,
    onRegister: () -> Unit,
    onDoneClick: () -> Unit,
    isValidEmailAndPassword: (String, String) -> Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    localFocusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val userState = uiState.collectAsStateWithLifecycle().value

    val isErrorEmail = userState.validating2 && !CheckInputValidation.isEmailValid(userState.email)
    val isErrorPassword =
        userState.validating2 && !CheckInputValidation.isPasswordValid(userState.password)
    val isErrorConfirmPassword = userState.password != userState.confirmPassword
            && userState.confirmPassword.isNotEmpty()


    val keyboardActions = KeyboardActions(
        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
        onDone = {
            keyboardController?.hide()
        }
    )
    val scrollState = rememberScrollState()
    val keyboardHeight = WindowInsets.ime.getTop(LocalDensity.current)

    LaunchedEffect(key1 = keyboardHeight) {
        scrollState.scrollBy(keyboardHeight.toFloat())
    }

    val registerStatus = registerState.collectAsState().value
    var showLoading by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = registerStatus) {
        Log.d("RegisterStatus", "$registerStatus")
        when (registerStatus) {
            is DataState.Loading -> {
                showLoading = true
                keyboardController?.hide()
            }

            is DataState.Success -> {
                showLoading = false
                onDoneClick()
            }

            is DataState.Error -> {
                showLoading = false
            }

            is DataState.Empty -> {}
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
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
            sheetContent = {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .imePadding()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Adjust the spacing between items
                ) {
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

                    Spacer(modifier = Modifier.weight(1f))
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
        )
        if (showLoading) {
            Loading()
        }
    }

}
