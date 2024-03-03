package com.company.khomasi.presentation.login

import android.util.Log
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.theme.Shapes
import com.company.khomasi.theme.darkHint
import com.company.khomasi.theme.darkSubText
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightHint
import com.company.khomasi.theme.lightSubText
import com.company.khomasi.theme.lightText

@Composable
fun LoginDataPage(
    isDark: Boolean,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onSuccessLogin: () -> Unit
) {
    val loginUiState = loginViewModel.uiState.value
    when (loginViewModel.loginState.collectAsState().value) {
        is DataState.Loading -> {
            Log.d("LoginDataPage", "Loading")
        }

        is DataState.Success -> {
            onSuccessLogin()
        }

        is DataState.Error -> {
            Log.d("LoginDataPage", "Error: ${loginViewModel.loginState.collectAsState().value}")
        }
    }
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
            value = loginUiState.email,
            onValueChange = loginViewModel::updateEmail,
            label = R.string.email,
            keyBoardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(24.dp))

        MyTextField(
            value = loginUiState.password,
            onValueChange = loginViewModel::updatePassword,
            label = R.string.password,
            keyBoardType = KeyboardType.Password,
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
                if (loginViewModel.isValidEmailAndPassword(
                        loginUiState.email,
                        loginUiState.password
                    )
                ) {
                    loginViewModel.login()
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
                .background(
                    color = Color.White,
                    shape = Shapes.extraLarge
                )
                .align(Alignment.CenterHorizontally)
                .clickable { loginViewModel.loginWithGmail() },
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
                    .clickable { loginViewModel.privacyAndPolicy() }
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
                    .clickable { loginViewModel.helpAndSupport() }

            )
        }
    }
}