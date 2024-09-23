package com.company.rentafield.presentation.screens.profile

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.presentation.screens.profile.components.content.ProfileContent
import com.company.rentafield.presentation.screens.profile.components.sheets.FeedbackBottomSheet
import com.company.rentafield.presentation.screens.profile.components.sheets.LogoutBottomSheet
import com.company.rentafield.presentation.screens.profile.components.topbar.ProfileTopBar
import com.company.rentafield.presentation.theme.RentafieldTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewProfile(
    profileUiState: StateFlow<ProfileUiState>,
    localUserUiState: StateFlow<LocalUser>,
    getProfileImage: () -> Unit,
    onLogout: () -> Unit,
    updateUserData: (LocalUser) -> Unit,
    sendFeedback: () -> Unit,
    onFeedbackChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    onEditProfile: () -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    onFeedbackCategorySelected: (FeedbackCategory) -> Unit,
) {
    val localUser = localUserUiState.collectAsStateWithLifecycle().value
    val uiState = profileUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(localUser) {
        updateUserData(localUser)
        getProfileImage()
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

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileTopBar(
            localUser = localUser,
            image = uiState.oldProfileImage,
            onEditProfile = onEditProfile,
            onBackClick = onBackClick,
            isDark = isDark
        )

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


@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun ProfileScreenPreview() {
    val profileViewModel = MockProfileViewModel()
    RentafieldTheme {
        ViewProfile(
            profileUiState = profileViewModel.profileUiState,
            localUserUiState = profileViewModel.localUser,
            onLogout = profileViewModel::onLogout,
            onBackClick = {},
            onEditProfile = {},
            onFeedbackCategorySelected = {},
            onFeedbackChanged = {},
            sendFeedback = {},
            updateUserData = {},
            getProfileImage = {},
        )
    }
}