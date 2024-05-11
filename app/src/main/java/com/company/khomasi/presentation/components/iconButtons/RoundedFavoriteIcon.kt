package com.company.khomasi.presentation.components.iconButtons

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkIcon
import com.company.khomasi.theme.lightIcon

@Composable
fun RoundedFavoriteIcon(
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {
    Box(
        modifier = modifier
            .width(44.dp)
            .height(44.dp)
            .background(
                color = if (isFavorite) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )
            .clickable {
                onFavoriteClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = null,
            modifier = modifier
                .padding(10.dp)
                .fillMaxSize(),
            tint = if (isDark) darkIcon else lightIcon
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RoundedFavoriteIconPreview() {
    KhomasiTheme {
        RoundedFavoriteIcon(
            onFavoriteClick = {},
            isFavorite = true
        )
    }
}
