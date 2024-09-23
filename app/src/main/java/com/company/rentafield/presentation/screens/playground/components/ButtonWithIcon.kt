package com.company.rentafield.presentation.screens.playground.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.company.rentafield.presentation.theme.darkIcon
import com.company.rentafield.presentation.theme.lightIcon

@Composable
fun ButtonWithIcon(
    iconId: Int, onClick: () -> Unit
) {
    val currentLanguage = LocalLayoutDirection.current
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .size(44.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) darkIcon else lightIcon,
            modifier = Modifier
                .size(24.dp)
                .then(
                    if (currentLanguage == LayoutDirection.Ltr) {
                        Modifier.rotate(180f)
                    } else {
                        Modifier
                    }
                )
        )
    }
}