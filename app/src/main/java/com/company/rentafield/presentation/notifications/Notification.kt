package com.company.rentafield.presentation.notifications

import androidx.compose.ui.text.AnnotatedString

data class Notification(
    val time: String,
    val title: String,
    val subTitle: AnnotatedString
)
