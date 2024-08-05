package com.company.rentafield.presentation.screens.playground.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.theme.darkIconMask
import com.company.rentafield.theme.lightIconMask

@Composable
fun PlaygroundStatus(
    status: String
) {
    Box {
        Card(
            modifier = Modifier.size(width = 99.dp, height = 40.dp),
            colors = CardDefaults.cardColors(if (isSystemInDarkTheme()) darkIconMask else lightIconMask),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 0.dp,
                bottomStart = 4.dp,
                bottomEnd = 0.dp
            )
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = status,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
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
fun PlaygroundStatusPreview() {
    RentafieldTheme {
        PlaygroundStatus(status = "Available")
    }
}

