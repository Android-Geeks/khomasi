package com.company.khomasi.presentation.profile

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.presentation.profile.components.profile_content.ProfileContent
import com.company.khomasi.presentation.profile.components.profile_topbar.ProfileTopBar
import com.company.khomasi.presentation.profile.components.sheets.FeedbackBottomSheet
import com.company.khomasi.presentation.profile.components.sheets.LogoutBottomSheet
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileUiState: StateFlow<ProfileUiState>,
    localUserUiState: StateFlow<LocalUser>,
    onLogout: () -> Unit,
    updateUserData: (LocalUser) -> Unit,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onSaveProfile: () -> Unit,
    sendFeedback: () -> Unit,
    onFeedbackChanged: (String) -> Unit,
    onChangeProfileImage: (File) -> Unit,
    onBackClick: () -> Unit,
    onEditProfile: (Boolean) -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    onFeedbackCategorySelected: (FeedbackCategory) -> Unit,
) {
    val uiState = profileUiState.collectAsState().value
    val localUser = localUserUiState.collectAsState().value

    LaunchedEffect(localUser) {
        updateUserData(localUser)
    }

    BackHandler {
        if (uiState.isEditPage) {
            onEditProfile(false)
        } else {
            onBackClick()
        }
    }

    if (uiState.isEditPage) {
        EditProfileScreen(
            oldLocalUser = localUser,
            localUser = uiState.user,
            onSaveProfile = onSaveProfile,
            onFirstNameChange = onFirstNameChanged,
            onLastNameChange = onLastNameChanged,
            onPhoneChange = onPhoneChanged,
            onBackClick = { onEditProfile(false) },
            onChangeProfileImage = onChangeProfileImage,
            isDark = isDark
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileTopBar(
                localUser = localUser,
                onEditProfile = onEditProfile,
                onBackClick = onBackClick,
                isDark = isDark
            )

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
                    scope.launch {
                        showLogoutSheet = true
                        bottomSheetState.show()
                    }
                },
                onShareYourOpinion = {
                    scope.launch {
                        showShareOpinionSheet = true
                        bottomSheetState.show()
                    }
                }
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
            updateUserData = {},
            onFirstNameChanged = {},
            onLastNameChanged = {},
            onPhoneChanged = {},
            onChangeProfileImage = {}
        )
    }
}