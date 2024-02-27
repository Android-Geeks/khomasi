package com.company.khomasi.presentation.ui.screens.logInScreen

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun LogIn(
    modifier: Modifier = Modifier,
    logInViewModel: LogInViewModel = viewModel()
) {
    val logInState by logInViewModel.uiState.collectAsState()

        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

                Text(
                    text = stringResource(id = R.string.you_are_almost_there),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier.fillMaxWidth()
                )
                Spacer(modifier = modifier.height(24.dp))

                    MyTextField(
                        value = logInViewModel.email ,
                        onValueChange ={logInViewModel.updateEmail(it)} ,
                        label = R.string.email ,
                        onImeAction = {ImeAction.Done},
                        keyBoardType = KeyboardType.Text
                    )
                Spacer(modifier = modifier.height(24.dp))

                MyTextField(
                    value = logInViewModel.password ,
                    onValueChange ={logInViewModel.updatePassword(it)} ,
                    label = R.string.password ,
                    onImeAction = {ImeAction.Done},
                    keyBoardType = KeyboardType.Password,

                )
                Text(
                    text = stringResource(id = R.string.forgot_your_password),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                    modifier = modifier
                        .clickable {logInViewModel.createAccount() }
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = modifier.height(24.dp))

                MyButton(
                    text = R.string.login,
                    onClick = {
                              logInViewModel.logIn()
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                )
                Spacer(modifier = modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxWidth()
                ) {

                    Text(
                        text = stringResource(id = R.string.do_not_have_an_account),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = stringResource(id = R.string.create_an_account_now),
                        modifier = modifier
                            .clickable {
                                       logInViewModel.createAccount()
                            },
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall

                    )

                }
                Spacer(modifier = modifier.height(24.dp))

                Row(modifier = modifier.fillMaxWidth()) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = modifier
                            .weight(1.5f)
                            .padding(start = 34.dp, end = 3.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = stringResource(id = R.string.or_register_via),
                        modifier = modifier
                            .weight(0.9f)
                            .padding(start = 5.dp),
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = modifier
                            .weight(1.5f)
                            .padding(start = 3.dp, end = 34.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = modifier.height(18.dp))


                Image(
                    painter = painterResource(id = R.drawable.icons_google),
                    contentDescription = "null",
                    modifier = modifier
                        .size(32.dp)
                        .clickable {
                            logInViewModel.logo()
                        }
                )

                Spacer(modifier = modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.by_registering_you_agree_to),
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxWidth()
                ) {

                    Text(
                        text = stringResource(id = R.string.help_and_support),
                        modifier = modifier
                            .clickable {
                                       logInViewModel.helpAndSupport()
                            },
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = stringResource(id = R.string.and),
                    )
                    Text(
                        text = stringResource(id = R.string.privacy_policy),
                        modifier = modifier
                            .clickable {
                                       logInViewModel.privacyAndPolicy()
                        },
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodySmall

                    )

                }
            }
        }
    }



@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LogInPreview() {
    KhomasiTheme {

        LogIn()

    }
}