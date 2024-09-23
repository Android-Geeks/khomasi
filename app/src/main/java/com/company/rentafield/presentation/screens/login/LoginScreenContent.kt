package com.company.rentafield.presentation.screens.login

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.AuthSheet
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyTextField
import com.company.rentafield.presentation.components.connectionStates.Loading
import com.company.rentafield.presentation.theme.darkHint
import com.company.rentafield.presentation.theme.darkSubText
import com.company.rentafield.presentation.theme.lightHint
import com.company.rentafield.presentation.theme.lightSubText


@Composable
fun LoginScreenContent(
    state: LoginReducer.State,
    login: () -> Unit,
    sendEvent: (LoginReducer.Event) -> Unit,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
        onDone = {
            keyboardController?.hide()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AuthSheet(
                modifier = Modifier,
                screenContent = {
                    Image(
                        painter =
                        if (isDark)
                            painterResource(id = R.drawable.dark_starting_player)
                        else
                            painterResource(id = R.drawable.light_starting_player),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
            ) {
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
                        value = state.email,
                        onValueChange = { sendEvent(LoginReducer.Event.UpdateEmail(it)) },
                        label = R.string.email,
                        keyBoardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        keyboardActions = keyboardActions,
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    MyTextField(
                        value = state.password,
                        onValueChange = { sendEvent(LoginReducer.Event.UpdatePassword(it)) },
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
                            .clickable { sendEvent(LoginReducer.Event.ForgotPasswordClick) },
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    MyButton(
                        text = R.string.login,
                        onClick = {
                            keyboardController?.hide()
                            login()
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
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(
                            text = stringResource(id = R.string.create_an_account_now),
                            modifier = Modifier
                                .clickable { sendEvent(LoginReducer.Event.RegisterClick) },
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
                            .clickable { },
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
                                .clickable { sendEvent(LoginReducer.Event.PrivacyAndPolicyClick) }
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
                                .clickable { sendEvent(LoginReducer.Event.HelpAndSupportClick) }

                        )
                    }
                }
            }
            if (state.isLoading) {
                Loading()
            }
        }
    }
}