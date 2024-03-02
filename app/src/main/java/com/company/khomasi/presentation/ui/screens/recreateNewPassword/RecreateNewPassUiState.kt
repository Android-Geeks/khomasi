package com.company.khomasi.presentation.ui.screens.recreateNewPassword

data class RecreateNewPassUiState(
    val userEmail : String = "hophop23103@gmail.com",
    val enteredVerificationCode : String = "",
    val realVerificationCode : String = "",
    val newPassword : String = "",
    val rewritingNewPassword  : String = "",
    val buttonEnable2 : Boolean = false,
    val isCodeTrue : Boolean = true
)
