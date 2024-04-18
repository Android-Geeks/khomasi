package com.company.khomasi.presentation.profile

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.presentation.profile.components.FeedbackBottomSheet
import com.company.khomasi.presentation.profile.components.LogoutBottomSheet
import com.company.khomasi.presentation.profile.components.ProfileContentItem
import com.company.khomasi.presentation.profile.components.ProfileImage
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkIconMask
import com.company.khomasi.theme.lightIconMask
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileUiState: StateFlow<ProfileUiState>,
    localUserUiState: StateFlow<LocalUser>,
    onLogout: () -> Unit,
    onSaveProfile: () -> Unit,
    sendFeedback: () -> Unit,
    onFeedbackChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    onEditProfile: (Boolean) -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    onFeedbackCategorySelected: (FeedbackCategory) -> Unit,
) {
    val uiState = profileUiState.collectAsState().value
    val localUser = localUserUiState.collectAsState().value
    BackHandler {
        if (uiState.isEditPage) {
            onEditProfile(false)
        } else {
            onBackClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(if (isDark) darkIconMask else lightIconMask)
                    .padding(16.dp),
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
                    onClick = { onEditProfile(true) },
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
                image = localUser.profilePicture,
                rating = localUser.rating ?: 0.0,
                isDark = isDark
            )
        }
        val bottomSheetState = rememberModalBottomSheetState()
        var showShareOpinionSheet by remember { mutableStateOf(false) }
        var showLogoutSheet by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        if (showShareOpinionSheet) {
            FeedbackBottomSheet(
                bottomSheetState = bottomSheetState,
                selectedCategory = uiState.feedbackCategory,
                onFeedbackSelected = onFeedbackCategorySelected,
                feedback = uiState.feedback,
                onFeedbackChanged = onFeedbackChanged,
                onDismissRequest = {
                    scope.launch {
                        bottomSheetState.hide()
                        showShareOpinionSheet = false
                    }
                },
                sendFeedback = sendFeedback,
                scope = scope,
                isDark = isDark
            )
        }
        if (showLogoutSheet) {
            LogoutBottomSheet(
                bottomSheetState = bottomSheetState,
                onDismissRequest = {
                    scope.launch {
                        bottomSheetState.hide()
                        showLogoutSheet = false
                    }
                },
                logout = onLogout,
                scope = scope,
                isDark = isDark
            )
        }

        ProfileContent(
            onLogout = {
                showLogoutSheet = true
            },
            onShareYourOpinion = {
                showShareOpinionSheet = true
            }
        )
    }
}

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


@Preview(
    name = "light",
    locale = "ar",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark",
    locale = "en",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileScreenPreview() {
    val profileViewModel = MockProfileViewModel()
    KhomasiTheme {
        ProfileScreen(
            profileUiState = profileViewModel.profileUiState,
            localUserUiState = profileViewModel.localUser,
            onLogout = profileViewModel::onLogout,
            onSaveProfile = profileViewModel::onSaveProfile,
            onBackClick = {},
            onEditProfile = profileViewModel::onEditProfile,
            onFeedbackCategorySelected = {},
            onFeedbackChanged = {},
            sendFeedback = {},
        )
    }
}