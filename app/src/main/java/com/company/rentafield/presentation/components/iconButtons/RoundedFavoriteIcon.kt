package com.company.rentafield.presentation.components.iconButtons

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.theme.darkIcon
import com.company.rentafield.theme.lightIcon

@Composable
fun RoundedFavoriteIcon(
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {
    IconButton(
        onClick = onFavoriteClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = if (isFavorite) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .size(44.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = null,
            tint = if (isDark) darkIcon else lightIcon,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RoundedFavoriteIconPreview() {
    RentafieldTheme {
        RoundedFavoriteIcon(
            onFavoriteClick = {},
            isFavorite = true
        )
    }
}
