package com.company.khomasi.presentation.ui.screens.recreateNewPassword

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.VerificationResponse


@Composable
fun RecreatePassScreen2(
    recreateViewModel: RecreateNewPassViewModel = hiltViewModel(),
) {
    val recreateUiState by recreateViewModel.recreateUiState.collectAsState()

    val res by recreateViewModel.verificationRes.collectAsState()
    val code = when(res){
        is DataState.Loading -> "Loading..."
        is DataState.Success -> (res as DataState.Success<VerificationResponse>).data.code.toString()
        is DataState.Error -> (res as DataState.Error).message
    }

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
                .padding(bottom = 8.dp)
                .clickable { recreateViewModel.onClickButton() },
            style = MaterialTheme.typography.titleMedium
        )

        Text(
//            text = stringResource(id = R.string.create_new_password),
            text = code,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(56.dp))


        MyTextField(
            value = recreateUiState.enteredVerificationCode ,
            onValueChange = {recreateViewModel.onEnteringVerificationCode(it)},
            label = R.string.verification_code ,
            onImeAction = {
                recreateViewModel.verifyVerificationCode(code)
                          },
            keyBoardType = KeyboardType.Number,
            isError = !recreateUiState.isCodeTrue
        )

        Spacer(modifier = Modifier.height(32.dp))

        MyTextField(
            value = recreateUiState.newPassword ,
            onValueChange = { recreateViewModel.onEnteringPassword(it) } ,
            label = R.string.new_password ,
            onImeAction = {
                // TODO:
            },
            keyBoardType = KeyboardType.Password
        )

        PasswordStrengthMeter(
            password = recreateUiState.newPassword,
            enable = recreateUiState.newPassword != ""
            )

        Text(
            text = stringResource(id = R.string.password_restrictions),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSystemInDarkTheme()) darkHint else lightHint
        )
        Spacer(modifier = Modifier.height(32.dp))

        MyTextField(
            value = recreateUiState.rewritingNewPassword ,
            onValueChange = {recreateViewModel.onReTypingPassword(it)},
            label = R.string.retype_password,
            onImeAction = {
                // TODO
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
            shape = MaterialTheme.shapes.medium,
            buttonEnable = recreateViewModel.checkValidation(recreateUiState.newPassword)
        )

        MyTextButton(
            text = R.string.back_to_login,
            onClick = {
                      },
            )
    }
}

@Composable
fun PasswordStrengthMeter(
    password: String,
    enable: Boolean         // Change happened
                            // "to avoid coloring the first partition when passStrength is 0 "
) {
    val passwordStrength = Zxcvbn().measure(password).score

    val indicatorColoringRange by remember {
        mutableStateOf(listOf(
            listOf(0, 1, 2, 3, 4),
            listOf(2, 3, 4),
            listOf(3, 4),
            listOf(4)
        ))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ){
        indicatorColoringRange.forEach { config ->
            PasswordStrengthIndicator(
                passwordStrength = passwordStrength,
                coloringEnableRange = config,
                modifier = Modifier.weight(1f),
                enable = enable
            )
        }
    }
}

@Composable
fun PasswordStrengthIndicator(
    passwordStrength: Int,
    coloringEnableRange: List<Int>,
    modifier : Modifier = Modifier,
    enable : Boolean = false,
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
        if (passwordStrength in coloringEnableRange && enable){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = when (passwordStrength) {
                            0, 1 -> Color.Red
                            2, 3 -> Color(0xFFF3F311)
                            4 -> Color.Green
                            else -> Color(0xFFDDDDDD)
                        }
                    )
            )
        }
    }
}

//@Preview(name = "Night", showSystemUi = true,uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ar", showSystemUi = true)
@Composable
fun RecreateNewPasswordPreview() {
    KhomasiTheme {
        RecreatePassScreen2()
    }
}
