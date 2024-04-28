package com.company.khomasi.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter


fun parseTimestamp(timestamp: String): LocalDateTime {
    return try {
        val offsetDateTime = OffsetDateTime.parse(timestamp)
        offsetDateTime.toLocalDateTime()
    } catch (e: Exception) {
        // If parsing fails, assume timestamp is in UTC time
        LocalDateTime.parse(timestamp)
    }
}

fun extractTimeFromTimestamp(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

fun extractDateFromTimestamp(localDateTime: LocalDateTime, format: String = "dd-MM-yyyy"): String {
    return localDateTime.format(DateTimeFormatter.ofPattern(format))
}