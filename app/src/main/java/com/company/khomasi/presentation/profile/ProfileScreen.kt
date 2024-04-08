package com.company.khomasi.presentation.profile

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

@Composable
fun ProfileScreen(
    profileUiState: StateFlow<ProfileUiState>,
    onLogout: KFunction0<Unit>,
    onSaveProfile: KFunction0<Unit>,
    onBackClick: () -> Unit,
    onEditProfile: KFunction1<Boolean, Unit>
) {
    val state = profileUiState.collectAsState().value
    BackHandler {
        if (state.isEditPage) {
            onEditProfile(false)
        } else {
            onBackClick()
        }
    }


}


@Preview(name = "light", locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", locale = "en", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreview() {
    val profileViewModel = MockProfileViewModel()
    KhomasiTheme {
        ProfileScreen(
            profileUiState = profileViewModel.profileUiState,
            onLogout = profileViewModel::onLogout,
            onEditProfile = profileViewModel::onEditProfile,
            onSaveProfile = profileViewModel::onSaveProfile,
            onBackClick = {}
        )
    }
}