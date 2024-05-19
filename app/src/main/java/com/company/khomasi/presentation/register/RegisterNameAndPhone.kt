package com.company.khomasi.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.LatandLong
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.getUserLocation
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.CheckInputValidation
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegisterNameAndPhone(
    uiState: StateFlow<RegisterUiState>,
    updateLocation: (LatandLong) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    isValidNameAndPhoneNumber: (String, String, String) -> Boolean,
    onLoginClick: () -> Unit,
    onNextClick: () -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val userState = uiState.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (uiState.value.longitude == 0.0) {
        updateLocation(getUserLocation(context = context))
    }

    val isErrorFirstName =
        userState.validating1 && !(CheckInputValidation.isFirstNameValid(userState.firstName))
    val isErrorLastName =
        userState.validating1 && !(CheckInputValidation.isLastNameValid(userState.lastName))
    val isErrorPhoneNumber =
        userState.validating1 && !CheckInputValidation.isPhoneNumberValid(userState.phoneNumber)

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
        modifier = Modifier.systemBarsPadding(),
        sheetContent = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .imePadding()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Adjust the spacing between items
            ) {

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

                Spacer(modifier = Modifier.weight(1f))
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
}