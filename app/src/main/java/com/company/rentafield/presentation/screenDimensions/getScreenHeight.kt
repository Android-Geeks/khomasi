package com.company.rentafield.presentation.screenDimensions

import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getScreenHeight(): Float {
    val displayMetrics: DisplayMetrics = LocalContext.current.resources.displayMetrics
    return displayMetrics.heightPixels / displayMetrics.density
}