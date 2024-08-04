package com.company.rentafield.utils.screenDimensions

import android.content.Context
import android.util.DisplayMetrics

fun getScreenWidth(context: Context): Float {
    val displayMetrics: DisplayMetrics =
        context.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}