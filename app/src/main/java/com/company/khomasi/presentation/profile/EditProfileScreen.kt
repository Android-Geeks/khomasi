package com.company.khomasi.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.SubScreenTopBar
import com.company.khomasi.presentation.profile.components.PhotoSelectorView
import com.company.khomasi.theme.darkIconMask
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightIconMask
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.CheckInputValidation

@Composable
fun EditProfileScreen(
    oldLocalUser: LocalUser,
    localUser: LocalUser,
    onSaveProfile: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onChangeProfileImage: (String) -> Unit,
    onBackClick: () -> Unit,
    isDark: Boolean,
) {
    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
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

    val isErrorFirstName = !(CheckInputValidation.isFirstNameValid(localUser.firstName ?: ""))
    val isErrorLastName = !(CheckInputValidation.isLastNameValid(localUser.lastName ?: ""))
    val isErrorPhoneNumber = !CheckInputValidation.isPhoneNumberValid(localUser.phoneNumber ?: "")

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        SubScreenTopBar(
            title = R.string.edit_profile,
            onBackClick = onBackClick
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .imePadding()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.size(16.dp))

            PhotoSelectorView(
                oldPic = oldLocalUser.profilePicture,
                onChangeProfileImage = onChangeProfileImage
            )

            MyTextField(
                value = localUser.firstName ?: "",
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
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            MyTextField(
                value = localUser.lastName ?: "",
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
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = stringResource(R.string.contact_information),
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center
                ),
                color = if (isDark) darkText else lightText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(if (isDark) darkIconMask else lightIconMask)
            )

            MyTextField(
                value = localUser.phoneNumber ?: "",
                onValueChange = onPhoneChange,
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
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            MyButton(
                text = R.string.save_changes,
                onClick = {
                    onSaveProfile()
                },
                buttonEnable = !isErrorFirstName
                        && !isErrorLastName
                        && !isErrorPhoneNumber
                        && localUser != oldLocalUser,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}