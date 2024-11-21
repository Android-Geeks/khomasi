package com.company.rentafield.presentation.screens.loginorsignup

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyOutlinedButton
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.presentation.theme.darkHint
import com.company.rentafield.presentation.theme.darkSubText
import com.company.rentafield.presentation.theme.lightHint
import com.company.rentafield.presentation.theme.lightSubText

@Composable
fun LoginOrRegisterScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
    )
    {
        Spacer(modifier = Modifier.height(130.dp))
        Image(
            painter = painterResource(id = R.drawable.player),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(11.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(68.dp))

        MyOutlinedButton(
            text = R.string.create_account,
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MyButton(
            text = R.string.login,
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(34.dp))

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
                .background(color = Color.White)
                .align(Alignment.CenterHorizontally)
                .clickable { /*to make*/ },
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {

            Text(
                text = stringResource(id = R.string.privacy_policy),
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clickable { /*to make*/ }
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
                    .clickable { /*to make*/ }
            )
        }
    }
}

@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun SignUpPreview() {
    RentafieldTheme {
        LoginOrRegisterScreen(
            onLoginClick = { },
            onRegisterClick = { }
        )
    }
}