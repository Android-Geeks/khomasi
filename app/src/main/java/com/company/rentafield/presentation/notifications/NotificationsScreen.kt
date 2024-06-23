package com.company.rentafield.presentation.notifications

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.theme.KhomasiTheme
import com.company.rentafield.theme.darkOverlay
import com.company.rentafield.theme.lightOverlay
import java.util.Locale

private val MAIN_PADDING = 20.dp
private val SECONDARY_PADDING = 13.dp
private val MAIN_ICON_SIZE = Modifier.size(38.dp, 46.dp)
private val SECONDARY_ICON_SIZE = Modifier.size(26.dp, 27.dp)
private val MAIN_LINE_END_PADDING = 31.dp
private val SECONDARY_LINE_END_PADDING = 21.dp
private val MAIN_SPACER_HEIGHT = 16.dp
private val SECONDARY_SPACER_HEIGHT = 10.dp

@Composable
fun NotificationsScreen(
    onBackClicked: () -> Unit,
    onClickBackToHome: () -> Unit,
    notifications: List<Notification>
) {
    Scaffold(
        topBar = {
            NotificationTopBar(onBackClicked = onBackClicked)
        },
        modifier = Modifier.fillMaxSize(),

        ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background,
        ) {
            when (notifications.size) {
                0 -> EmptyNotification(onClickBackToHome = onClickBackToHome)
                else -> NotificationContent(notifications = notifications)
            }
        }
    }
}


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

@Composable
fun NotificationContent(notifications: List<Notification>) {
    LazyColumn {
        items(notifications.size) { index ->
            NotificationItem(notification = notifications[index])
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Row {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = notification.time,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = notification.subTitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )
    }
}

@Composable
fun DrawLine(lineColor: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxWidth()) {
        drawLine(
            color = lineColor,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTopBar(
    onBackClicked: () -> Unit,
) {
    val currentLanguage = Locale.getDefault().language
    Column(verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Spacer(modifier = Modifier.width(4.dp))
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.notifications),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }, navigationIcon = {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        Modifier
                            .size(24.dp)
                            .then(
                                if (currentLanguage == "en") {
                                    Modifier.rotate(180f)
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
    }
}

@Preview(showSystemUi = true, locale = "en")
@Composable
fun NotificationsPreview() {
    KhomasiTheme {
        NotificationsScreen(
            onBackClicked = { },
            onClickBackToHome = { },
            notifications = notificationsTestList
        )
    }
}

val notificationsTestList = listOf(
    Notification(
        time = "4:00 PM",
        title = "New field added near you",
        subTitle = "A new field has been added near you. Click here to view the field."
    ),
    Notification(
        time = "4:00 PM",
        title = "New field added near you",
        subTitle = "A new field has been added near you. Click here to view the field."
    ),
    Notification(
        time = "4:00 PM",
        title = "New field added near you",
        subTitle = "A new field has been added near you. Click here to view the field."
    ),
)