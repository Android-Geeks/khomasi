package com.company.rentafield.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


