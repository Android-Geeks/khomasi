package com.company.rentafield.presentation.notifications.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.notifications.DrawLine
import com.company.rentafield.theme.darkOverlay
import com.company.rentafield.theme.lightOverlay

private val MAIN_PADDING = 20.dp
private val SECONDARY_PADDING = 13.dp
private val MAIN_ICON_SIZE = Modifier.size(38.dp, 46.dp)
private val SECONDARY_ICON_SIZE = Modifier.size(26.dp, 27.dp)
private val MAIN_LINE_END_PADDING = 31.dp
private val SECONDARY_LINE_END_PADDING = 21.dp
private val MAIN_SPACER_HEIGHT = 16.dp
private val SECONDARY_SPACER_HEIGHT = 10.dp

@Composable
fun NotificationsCard(
    modifier: Modifier = Modifier, isMain: Boolean = true
) {
    val paddingValue = if (isMain) MAIN_PADDING else SECONDARY_PADDING
    val iconSize = if (isMain) MAIN_ICON_SIZE else SECONDARY_ICON_SIZE
    val lineEndPadding = if (isMain) MAIN_LINE_END_PADDING else SECONDARY_LINE_END_PADDING
    val spacerHeight = if (isMain) MAIN_SPACER_HEIGHT else SECONDARY_SPACER_HEIGHT
    val cardColors =
        if (isMain) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.surface
    val bellColor =
        if (isMain) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceContainer

    Card(
        colors = CardDefaults.cardColors(cardColors),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cancel),
                    tint = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier
                        .padding(end = 13.dp, top = 10.dp, bottom = 2.dp)
                        .then(if (isMain) Modifier.size(10.dp) else Modifier.size(7.dp)),
                    contentDescription = null,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = paddingValue, end = paddingValue, bottom = paddingValue),
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    modifier = Modifier.then(iconSize),
                    painter = painterResource(id = R.drawable.filled_bell),
                    contentDescription = null,
                    tint = bellColor
                )
                Column(
                    Modifier.padding(top = 8.dp, start = 16.dp, end = 20.dp)
                ) {
                    DrawLine(
                        if (isSystemInDarkTheme()) darkOverlay else lightOverlay,
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                Modifier.padding(end = lineEndPadding)
                            )
                    )
                    Spacer(modifier = Modifier.then(Modifier.height(spacerHeight)))

                    DrawLine(if (isSystemInDarkTheme()) darkOverlay else lightOverlay)

                    Spacer(modifier = Modifier.height(9.dp))

                    DrawLine(if (isSystemInDarkTheme()) darkOverlay else lightOverlay)
                }

            }
        }
    }
}