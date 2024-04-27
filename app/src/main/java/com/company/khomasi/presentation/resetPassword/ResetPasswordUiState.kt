package com.company.khomasi.presentation.resetPassword


data class ResetPasswordUiState(
    val userEmail: String = "",
    val enteredVerificationCode: String = "",
    val correctCode: String = "",
    val newPassword: String = "",
    val rewritingNewPassword: String = "",
    val buttonEnable2: Boolean = false,
    val isCodeTrue: Boolean = true,
    val validating1: Boolean = false,
    val validating2: Boolean = false,
)
