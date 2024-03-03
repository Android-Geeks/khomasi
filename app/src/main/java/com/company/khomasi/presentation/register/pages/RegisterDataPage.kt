package com.company.khomasi.presentation.register.pages

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.register.RegisterViewModel
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText

@Composable
fun RegisterDataPage(
    viewModel: RegisterViewModel,
    localFocusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    isDark: Boolean = isSystemInDarkTheme()
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
                Spacer(modifier = Modifier.height(84.dp))
                MyButton(
                    text = R.string.next,
                    onClick = { },
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
                Spacer(modifier = Modifier.height(84.dp))
                MyButton(
                    text = R.string.create_account,
                    onClick = {},
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
                onClick = { /*TODO*/ }
            )
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