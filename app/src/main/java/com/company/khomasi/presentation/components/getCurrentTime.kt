package com.company.khomasi.presentation.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getCurrentTime() : String{
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000) // Update every second
        }
    }

    val formattedTimeHours = remember(currentTime) {
        DateTimeFormatter.ofPattern("HH:mm:ss").format(currentTime)
//        DateTimeFormatter.ofPattern("HH").format(currentTime)
    }
    Log.d("Time", "Current Time: $currentTime")
    Log.d("Time", "format Time: $formattedTimeHours")
    return formattedTimeHours

}



