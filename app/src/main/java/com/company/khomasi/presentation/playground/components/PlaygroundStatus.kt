package com.company.khomasi.presentation.playground.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.company.khomasi.theme.darkIconMask
import com.company.khomasi.theme.lightIconMask

@Composable
fun PlaygroundStatus(
    status: String
) {
    Box {
        Card(
            modifier = Modifier.size(width = 99.dp, height = 40.dp),
            colors = CardDefaults.cardColors(if (isSystemInDarkTheme()) darkIconMask else lightIconMask),
            shape = RectangleShape
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
