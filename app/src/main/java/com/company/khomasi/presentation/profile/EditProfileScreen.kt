package com.company.khomasi.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.presentation.components.LatandLong
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.SubScreenTopBar
import com.company.khomasi.theme.darkIconMask
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightIconMask
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.CheckInputValidation
import com.company.khomasi.utils.convertToBitmap

@Composable
fun EditProfileScreen(
    oldLocalUser: LocalUser,
    localUser: LocalUser,
    onSaveProfile: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onLocationChange: (LatandLong) -> Unit,
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
    val isErrorEmail = !CheckInputValidation.isEmailValid(localUser.email ?: "")

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .imePadding()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SubScreenTopBar(
            title = R.string.edit_profile,
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.size(16.dp))

        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(localUser.profilePicture?.convertToBitmap())
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.playground),
            error = painterResource(id = R.drawable.user_img),
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
        )

        MyTextButton(
            text = R.string.change,
            onClick = { },
            isUnderlined = false
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
            value = localUser.email ?: "",
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
            },
            modifier = Modifier.padding(horizontal = 16.dp)
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


        Text(
            text = stringResource(R.string.location),
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center
            ),
            color = if (isDark) darkText else lightText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(if (isDark) darkIconMask else lightIconMask)
        )


        MyButton(
            text = R.string.save_changes,
            onClick = {
                onSaveProfile()
            },
            buttonEnable = !isErrorFirstName
                    && !isErrorLastName
                    && !isErrorPhoneNumber
                    && !isErrorEmail
                    && localUser != oldLocalUser,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}