package com.company.rentafield.presentation.screens.notifications

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.screens.notifications.component.NotificationItem
import com.company.rentafield.presentation.screens.search.components.MyTopAppBar
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.utils.extractTimeFromTimestamp
import com.company.rentafield.utils.parseTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun NotificationsScreen(
    onBackClicked: () -> Unit,
    notificationStateFlow: StateFlow<DataState<com.company.rentafield.data.models.ai.AiResponse>>,
    getNotifications: () -> Unit,
    localUserUiState: StateFlow<com.company.rentafield.data.models.LocalUser>
) {
    val localUser = localUserUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(localUser) {
        getNotifications()
    }
    Scaffold(
        topBar = {
            MyTopAppBar(
                onBackClick = onBackClicked,
                title = R.string.notifications,
                isDark = isSystemInDarkTheme()
            )
        },
        modifier = Modifier.fillMaxSize(),

        ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background,
        ) {
            val notificationState by notificationStateFlow.collectAsStateWithLifecycle()
            val aiResponse = remember {
                mutableStateOf<List<com.company.rentafield.data.models.ai.AiLog>>(emptyList())
            }
            LaunchedEffect(notificationState) {
                Log.d("notificationState", notificationState.toString())
                when (notificationState) {
                    is DataState.Success -> {
                        aiResponse.value = (notificationState as DataState.Success).data.aiLogs
                    }

                    else -> {}
                }
            }

            when (aiResponse.value.size) {
                0 -> EmptyNotification(onClickBackToHome = onBackClicked)
                else -> {
                    NotificationContent(notifications = aiResponse.value)
                }
            }
        }
    }
}

@Composable
fun NotificationContent(notifications: List<com.company.rentafield.data.models.ai.AiLog>) {
    LazyColumn {
        items(notifications) { log ->
            val notificationTime = extractTimeFromTimestamp(parseTimestamp(log.uploadDate))
            if (log.isProcessed) {
                NotificationItem(
                    notification = Notification(time = notificationTime,
                        title = stringResource(R.string.video_processed_successfully),
                        subTitle = buildAnnotatedString {
                            append(stringResource(R.string.successfully_your_kick_up_count_is) + " ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append(" ${log.kickupCount} ")
                            }
                            append(stringResource(R.string.you_ve_got))
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append(" ${((log.kickupCount) * 0.2).toInt()} ")
                            }
                            append(stringResource(id = R.string.coins))
                        })
                )

            } else {
                NotificationItem(
                    notification = Notification(time = notificationTime,
                        title = stringResource(R.string.video_processing),
                        subTitle = buildAnnotatedString { append(stringResource(R.string.wait_your_video_is_being_processed)) })
                )
            }
        }
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
fun NotificationsPreview() {
    RentafieldTheme {
        NotificationsScreen(
            onBackClicked = { },
            getNotifications = {},
            localUserUiState = MutableStateFlow(com.company.rentafield.data.models.LocalUser()),
            notificationStateFlow = MutableStateFlow(
                DataState.Success(
                    com.company.rentafield.data.models.ai.AiResponse(
                        aiLogs = listOf(
                            com.company.rentafield.data.models.ai.AiLog(
                                isProcessed = true,
                                kickupCount = 1,
                                uploadDate = "2021-09-01T00:00:00.00"
                            ),
                            com.company.rentafield.data.models.ai.AiLog(
                                isProcessed = false,
                                kickupCount = 1,
                                uploadDate = "2021-09-01T00:00:00.00"
                            )
                        )
                    )
                )
            )
        )
    }
}
