package com.company.khomasi.presentation.ui.screens.recreateNewPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkHint
import com.company.khomasi.theme.lightHint
import com.nulabinc.zxcvbn.Zxcvbn


@Composable
fun RecreateNewPasswordScreen(
    recreateNewPassViewModel: RecreateNewPassViewModel
) {
    val recreateNewPassUiState by recreateNewPassViewModel.recreateUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.reset_password),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = stringResource(id = R.string.create_new_password),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(56.dp))

        MyTextField(
            value = recreateNewPassUiState.verificationCode ,
            onValueChange = {recreateNewPassViewModel.verifyVerificationCode(it)},
            label = R.string.verification_code ,
            onImeAction = {
                // TODO:
            },
            keyBoardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(32.dp))

        MyTextField(
            value = recreateNewPassUiState.newPassword ,
            onValueChange = { recreateNewPassViewModel.onEnteringPassword(it) } ,
            label = R.string.new_password ,
            onImeAction = {
                // TODO:
            },
            keyBoardType = KeyboardType.Password
        )

        PasswordStrengthMeter(recreateNewPassUiState.newPassword)

        Text(
            text = stringResource(id = R.string.password_restrictions),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSystemInDarkTheme()) darkHint else lightHint
        )
        Spacer(modifier = Modifier.height(32.dp))

        MyTextField(
            value = recreateNewPassUiState.rewritingNewPassword ,
            onValueChange = {recreateNewPassViewModel.onReTypingPassword(it)},
            label = R.string.retype_password,
            onImeAction = {
                // TODO:

            },
            keyBoardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(32.dp))

        MyButton(
            text = R.string.set_password,
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            buttonEnable = recreateNewPassUiState.buttonEnable
        )

        MyTextButton(
            text = R.string.back_to_login,
            onClick = { /*TODO*/ },
            )
    }
}

@Composable
fun PasswordStrengthMeter(password: String) {

    val passwordStrength = Zxcvbn().measure(password).score

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ){
        PasswordStrengthIndicator(
            passwordStrength = passwordStrength,
            listOf(0, 1, 2, 3, 4),
            Modifier.weight(1f)
        )
        PasswordStrengthIndicator(
            passwordStrength = passwordStrength,
            listOf(2, 3, 4),
            Modifier.weight(1f)
        )
        PasswordStrengthIndicator(
            passwordStrength = passwordStrength,
            listOf(3, 4),
            Modifier.weight(1f)
        )
        PasswordStrengthIndicator(
            passwordStrength = passwordStrength,
            listOf(4),
            Modifier.weight(1f)
        )
    }
}

@Composable
fun PasswordStrengthIndicator(
    passwordStrength: Int,
    coloringEnableRange: List<Int>,
    modifier : Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .padding(horizontal = 4.dp)
            .background(
                color = Color(0xFFDDDDDD),
                shape = RoundedCornerShape(100.dp)
            )
    ){
        if (passwordStrength in coloringEnableRange){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = when (passwordStrength) {
                            1 -> Color.Red
                            2 -> Color.Yellow
                            3, 4 -> Color.Green
                            else -> Color(0xFFDDDDDD)
                        }
                    )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RecreateNewPasswordPreview() {
    KhomasiTheme(darkTheme = false) {
        RecreateNewPasswordScreen(
            RecreateNewPassViewModel()
        )
    }
}
