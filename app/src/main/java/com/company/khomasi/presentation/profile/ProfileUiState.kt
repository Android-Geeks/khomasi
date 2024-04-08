package com.company.khomasi.presentation.profile

import com.company.khomasi.domain.model.LocalUser

data class ProfileUiState(
    val user: LocalUser = LocalUser(),
    val isEditPage: Boolean = false
)
