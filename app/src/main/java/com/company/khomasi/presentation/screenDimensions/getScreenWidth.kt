package com.company.khomasi.presentation.screenDimensions

import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getScreenWidth(): Float {
    val displayMetrics: DisplayMetrics =
        LocalContext.current.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}