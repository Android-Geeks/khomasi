package com.company.rentafield.presentation.screens.notifications

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
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.screens.notifications.component.NotificationsCard


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
            text = stringResource(R.string.donot_have_notifications),
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