package com.company.khomasi.presentation.ui.screens.recreateNewPassword

data class RecreateNewPassUiState(
    val verificationCode : String = "",
    val newPassword : String = "",
    val rewritingNewPassword  : String = "",
    val buttonEnable : Boolean = false,
    val isCodeTrue : Boolean = false
)
