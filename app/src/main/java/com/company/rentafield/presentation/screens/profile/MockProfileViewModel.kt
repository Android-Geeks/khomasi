package com.company.rentafield.presentation.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockProfileViewModel : ViewModel() {

    val profileUiState: StateFlow<ProfileUiState> = MutableStateFlow(
        ProfileUiState(
            user = com.company.rentafield.data.models.LocalUser()
        )
    )
    val localUser: StateFlow<com.company.rentafield.data.models.LocalUser> =
        MutableStateFlow(com.company.rentafield.data.models.LocalUser())


    fun onLogout() {}


}