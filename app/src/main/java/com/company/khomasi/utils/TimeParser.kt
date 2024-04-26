package com.company.khomasi.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun parseTimestamp(timestamp: String): LocalDateTime {
    return try {
        val offsetDateTime = OffsetDateTime.parse(timestamp)
        offsetDateTime.toLocalDateTime()
    } catch (e: Exception) {
        // If parsing fails, assume timestamp is in UTC time
        LocalDateTime.parse(timestamp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun extractTimeFromTimestamp(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

@RequiresApi(Build.VERSION_CODES.O)
fun extractDateFromTimestamp(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}