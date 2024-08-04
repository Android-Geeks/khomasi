package com.company.rentafield.presentation.screens.profile.components.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.company.rentafield.R

@Composable
fun ProfileContent(
    onLogout: () -> Unit,
    onShareYourOpinion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
        ) {
            ProfileContentItem(
                title = R.string.share_your_feedback,
                icon = R.drawable.chatcircledots,
                onclick = onShareYourOpinion
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.tertiary
            )
            ProfileContentItem(
                title = R.string.rate_the_app,
                icon = R.drawable.appstorelogo,
                onclick = {}
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.tertiary
            )
            ProfileContentItem(
                title = R.string.share_the_app,
                icon = R.drawable.sharenetwork,
                onclick = {}
            )
        }
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
        ) {
            ProfileContentItem(
                title = R.string.help_and_support,
                icon = R.drawable.info,
                onclick = {}
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.tertiary
            )
            ProfileContentItem(
                title = R.string.terms_of_service,
                icon = R.drawable.shieldwarning,
                onclick = {}
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.tertiary
            )
            ProfileContentItem(
                title = R.string.logout,
                icon = R.drawable.signout,
                onclick = onLogout
            )
        }
    }
}