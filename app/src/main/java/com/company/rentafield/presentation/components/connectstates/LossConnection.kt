package com.company.rentafield.presentation.components.connectstates

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.presentation.theme.darkSubText
import com.company.rentafield.presentation.theme.lightSubText


@Composable
fun LossConnection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = if (isSystemInDarkTheme()) {
                painterResource(id = R.drawable.loss_connection_light)
            } else {
                painterResource(id = R.drawable.loss_connection_dark)
            },
            contentDescription = null,
            modifier = Modifier.size(161.dp, 130.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.internet_connection_lost),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.check_network_status),
            style = MaterialTheme.typography.titleMedium,
            color = if (isSystemInDarkTheme()) darkSubText else lightSubText
        )
    }

}

@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ar")
@Composable
fun LossConnectionPreview() {
    RentafieldTheme(darkTheme = true) {
        LossConnection()
    }
}