package com.company.khomasi.presentation.resetPassword

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MessageResponse
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.PasswordStrengthMeter
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.utils.CheckInputValidation
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun PasswordConfirmation(
    uiState: StateFlow<ResetPasswordUiState>,
    onBackToLogin: () -> Unit,
    recoverResponse: StateFlow<DataState<MessageResponse>>,
    onEnteringVerificationCode: (String) -> Unit,
    verifyVerificationCode: (String) -> Unit,
    onEnteringPassword: (String) -> Unit,
    onReTypingPassword: (String) -> Unit,
    onButtonClickedScreen2: () -> Unit,
) {
    val resetUiState = uiState.collectAsStateWithLifecycle().value
    val recoverStatus = recoverResponse.collectAsStateWithLifecycle().value
    var showLoading by remember { mutableStateOf(false) }

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current

    val isPasswordError =
        resetUiState.validating2 && !CheckInputValidation.isPasswordValid(resetUiState.newPassword)
    val isPasswordMatchError = resetUiState.newPassword != resetUiState.rewritingNewPassword
            && resetUiState.rewritingNewPassword.isNotEmpty()


    LaunchedEffect(key1 = recoverStatus) {
        Log.d("RecoverStatus", "RecoverStatus: $recoverStatus")
        when (recoverStatus) {
            is DataState.Loading -> {
                showLoading = true
                keyboardController?.hide()
            }

            is DataState.Success -> {
                showLoading = false
                Toast.makeText(context, R.string.password_changed, Toast.LENGTH_SHORT).show()
                onBackToLogin()
            }

            is DataState.Error -> {
                showLoading = false
                when (recoverStatus.code) {
                    0 -> {
                        Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT)
                            .show()
                    }

                    400 -> {
                        Toast.makeText(
                            context,
                            R.string.invalid_verification_code,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
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
                        verifyVerificationCode(resetUiState.correctCode)
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
    if (showLoading) {
        Loading()
    }
}
