package com.company.rentafield.presentation.screens.notifications

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.screens.notifications.component.NotificationsCard
import com.company.rentafield.presentation.theme.RentafieldTheme


@Composable
fun EmptyNotification(
    onClickBackToHome: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        NotificationsCard(
            modifier = Modifier.padding(horizontal = 63.dp), isMain = true
        )

        Spacer(modifier = Modifier.height(13.dp))

        NotificationsCard(
            modifier = Modifier.padding(horizontal = 104.dp), isMain = false
        )

        Spacer(modifier = Modifier.height(8.dp))

        NotificationsCard(modifier = Modifier.padding(horizontal = 104.dp), isMain = false)

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.do_not_have_notifications),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        MyButton(
            text = R.string.back_to_home,
            onClick = onClickBackToHome,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(112.dp))
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
fun EmptyNotificationPreview() {
    RentafieldTheme {
        EmptyNotification(onClickBackToHome = {})
    }
}