package com.company.rentafield.presentation.home


data class HomeUiState(
    val viewAllSwitch: Boolean = false,
    val canUploadVideo: Boolean = false,
    val profileImage: String? = null
)
