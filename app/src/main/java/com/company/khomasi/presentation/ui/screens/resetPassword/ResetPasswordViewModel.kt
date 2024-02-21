package com.company.khomasi.presentation.ui.screens.resetPassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResetPasswordViewModel() : ViewModel() {

    private val _resetPasswordUiState = MutableStateFlow(ResetPasswordUiState())
    val resetPasswordUiState: StateFlow<ResetPasswordUiState> = _resetPasswordUiState.asStateFlow()

    fun onUserEmailChange(email: String) {
        _resetPasswordUiState.update {
            it.copy(
                userEmail = email
            )
        }
    }
}