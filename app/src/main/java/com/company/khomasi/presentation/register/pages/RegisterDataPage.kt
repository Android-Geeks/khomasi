package com.company.khomasi.presentation.register.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.register.RegisterViewModel
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun RegisterDataPage(
    viewModel: RegisterViewModel,
    localFocusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
) {
    val userState = viewModel.user.value
    val keyboardActions = KeyboardActions(
        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
        onDone = {
            localFocusManager.clearFocus()
            keyboardController?.hide()
        }
    )
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        when (userState.page) {
            1 -> {
                Text(
                    text = stringResource(id = R.string.please_confirm_your_information),
                    style = MaterialTheme.typography.displayMedium,
                )
                MyTextField(
                    value = userState.firstName,
                    onValueChange = { viewModel.onFirstNameChange(it) },
                    label = R.string.first_name,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Text,
                    keyboardActions = keyboardActions
                )
                MyTextField(
                    value = userState.lastName,
                    onValueChange = { viewModel.onLastNameChange(it) },
                    label = R.string.last_name,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Text,
                    keyboardActions = keyboardActions
                )
                MyTextField(
                    value = userState.phoneNumber,
                    onValueChange = { viewModel.onPhoneNumberChange(it) },
                    label = R.string.phone_number,
                    imeAction = ImeAction.Done,
                    keyBoardType = KeyboardType.Phone,
                    keyboardActions = keyboardActions
                )
                MyButton(
                    text = R.string.next,
                    onClick = { viewModel.onNextPage() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            2 -> {
                Text(
                    text = stringResource(id = R.string.you_are_almost_there),
                    style = MaterialTheme.typography.displayMedium,
                )
                MyTextField(
                    value = userState.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = R.string.email,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Email,
                    keyboardActions = keyboardActions
                )
                MyTextField(
                    value = userState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = R.string.password,
                    imeAction = ImeAction.Next,
                    keyBoardType = KeyboardType.Password,
                    keyboardActions = keyboardActions
                )
                MyTextField(
                    value = userState.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChange(it) },
                    label = R.string.confirm_password,
                    imeAction = ImeAction.Done,
                    keyBoardType = KeyboardType.Password,
                    keyboardActions = keyboardActions
                )
                MyButton(
                    text = R.string.create_account,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}

@Preview
@Composable
fun RegisterDataPagePreview() {
    KhomasiTheme {
        RegisterDataPage(
            viewModel = RegisterViewModel(),
        )
    }
}