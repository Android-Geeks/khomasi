package com.company.khomasi.presentation.resetPassword


data class ResetPasswordUiState(
    val userEmail: String = "",
    val enteredVerificationCode: String = "",
    val realVerificationCode: String = "",
    val newPassword: String = "",
    val rewritingNewPassword: String = "",
    val buttonEnable2: Boolean = false,
    val isCodeTrue: Boolean = true,
    val isTwoPassEquals: Boolean = true,
    val passwordStrength: Int = 0
)
