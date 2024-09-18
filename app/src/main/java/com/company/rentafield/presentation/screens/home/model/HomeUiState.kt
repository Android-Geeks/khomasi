package com.company.rentafield.presentation.screens.home.model

import androidx.compose.runtime.Stable

@Stable
data class HomeUiState(
    val viewAllSwitch: Boolean = false,
    val canUploadVideo: Boolean = false,
    val profileImage: String? = null
)
