package com.company.rentafield.presentation.screens.profile.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.theme.darkIconMask
import com.company.rentafield.presentation.theme.lightIconMask

@Composable
fun ProfileTopBar(
    localUser: com.company.rentafield.data.models.LocalUser,
    image: String?,
    onEditProfile: () -> Unit,
    onBackClick: () -> Unit,
    isDark: Boolean
) {
    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(if (isDark) darkIconMask else lightIconMask)
                .padding(start = 16.dp, end = 16.dp, top = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(44.dp)
                    .clip(
                        shape = CircleShape
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    modifier = if (LocalLayoutDirection.current == LayoutDirection.Ltr) Modifier.rotate(
                        180f
                    ) else Modifier,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(
                onClick = onEditProfile,
                modifier = Modifier
                    .size(44.dp)
                    .clip(
                        shape = CircleShape
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pencilsimpleline),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

        }
        ProfileImage(
            name = localUser.firstName + " " + localUser.lastName,
            image = image,
            rating = localUser.rating ?: 0.0,
            coins = localUser.coins ?: 0.0
        )
    }
}