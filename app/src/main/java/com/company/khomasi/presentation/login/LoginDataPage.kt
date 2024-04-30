package com.company.khomasi.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.theme.darkHint
import com.company.khomasi.theme.darkSubText
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightHint
import com.company.khomasi.theme.lightSubText
import com.company.khomasi.theme.lightText


@Composable
fun LoginDataPage(
    isDark: Boolean,
    modifier: Modifier = Modifier,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    loginUiState: State<LoginUiState>,
    updatePassword: (String) -> Unit,
    updateEmail: (String) -> Unit,
    login: () -> Unit,
    loginWithGmail: () -> Unit,
    privacyAndPolicy: () -> Unit,
    helpAndSupport: () -> Unit,
    isValidEmailAndPassword: (String, String) -> Boolean,
    keyboardController: SoftwareKeyboardController?,
) {
    val localFocusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
        onDone = {
            keyboardController?.hide()
        }
    )

    val uiState = loginUiState.value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .wrapContentHeight()
    ) {
        Text(
            text = stringResource(id = R.string.you_are_almost_there),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        MyTextField(
            value = uiState.email,
            onValueChange = updateEmail,
            label = R.string.email,
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            keyboardActions = keyboardActions,
        )
        Spacer(modifier = Modifier.height(24.dp))

        MyTextField(
            value = uiState.password,
            onValueChange = updatePassword,
            label = R.string.password,
            keyBoardType = KeyboardType.Password,
            keyboardActions = keyboardActions,
            imeAction = ImeAction.Done,
        )
        Text(
            text = stringResource(id = R.string.forgot_your_password),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onForgotPasswordClick() },
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(24.dp))

        MyButton(
            text = R.string.login,
            onClick = {
                if (isValidEmailAndPassword(
                        uiState.email,
                        uiState.password
                    )
                ) {
                    login()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 10.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.do_not_have_an_account),
                style = MaterialTheme.typography.bodySmall,
                color = if (isDark) darkText else lightText
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = stringResource(id = R.string.create_an_account_now),
                modifier = Modifier
                    .clickable { onRegisterClick() },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall

            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 34.dp, end = 3.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = stringResource(id = R.string.or_register_via),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = if (isDark) darkHint else lightHint
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 3.dp, end = 34.dp)
                    .align(Alignment.CenterVertically),
                thickness = 1.dp
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .clickable { loginWithGmail() },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons_google),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = stringResource(id = R.string.by_registering_you_agree_to),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = if (isDark) darkSubText else lightSubText,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stringResource(id = R.string.privacy_policy),
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clickable { privacyAndPolicy() }
            )

            Text(
                text = stringResource(id = R.string.and),
                style = MaterialTheme.typography.bodySmall,
                color = if (isDark) darkSubText else lightSubText,
                modifier = Modifier.padding(horizontal = 5.dp)
            )

            Text(
                text = stringResource(id = R.string.help_and_support),
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clickable { helpAndSupport() }

            )
        }
    }
}