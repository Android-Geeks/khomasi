package com.company.rentafield.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


private val lightThemeColors = lightColorScheme(
    primary = lightPrimary,
    onPrimary = lightBackground,
    onPrimaryContainer = lightText,
    secondary = lightSecondary,
    background = lightBackground,
    surface = lightCard,
    surfaceContainer = lightOverlay,
    tertiary = lightSubText,
    outline = lightHint,
    error = lightErrorColor,
)

private val darkThemeColors = darkColorScheme(
    primary = darkPrimary,
    onPrimary = darkBackground,
    onPrimaryContainer = darkText,
    secondary = darkSecondary,
    background = darkBackground,
    surface = darkCard,
    surfaceContainer = darkOverlay,
    tertiary = darkSubText,
    outline = darkHint,
    error = darkErrorColor
)

@Composable
fun RentafieldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkThemeColors
        else -> lightThemeColors
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


