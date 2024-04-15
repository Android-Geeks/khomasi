package com.company.khomasi.presentation.profile

import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.model.LocalUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockProfileViewModel : ViewModel() {

    val profileUiState: StateFlow<ProfileUiState> = MutableStateFlow(
        ProfileUiState(
            user = LocalUser()
        )
    )
    val localUser: StateFlow<LocalUser> = MutableStateFlow(LocalUser())


    fun onLogout() {}
    fun onEditProfile(isEdit: Boolean) {}
    fun onSaveProfile() {}


}