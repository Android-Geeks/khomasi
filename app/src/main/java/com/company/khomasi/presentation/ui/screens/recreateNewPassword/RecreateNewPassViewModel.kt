package com.company.khomasi.presentation.ui.screens.recreateNewPassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecreateNewPassViewModel : ViewModel() {
    private val _recreateUiState = MutableStateFlow(RecreateNewPassUiState())
    val recreateUiState: StateFlow<RecreateNewPassUiState> = _recreateUiState.asStateFlow()

    fun onEnteringVerificationCode(code: String) {
        _recreateUiState.update {
            it.copy(
                verificationCode = code
            )
        }
    }

    fun verifyVerificationCode() {
        _recreateUiState.update {
            it.copy(
                isCodeTrue = _recreateUiState.value.verificationCode != "12345"
            )
        }
    }

    fun onEnteringPassword(password: String) {
        _recreateUiState.update {
            it.copy(
                newPassword = password
            )
        }
    }

    fun onReTypingPassword(password: String) {
        _recreateUiState.update {
            it.copy(
                rewritingNewPassword = password,
            )
        }
    }

    fun checkValidation(password: String): Boolean {
        _recreateUiState.update {
            it.copy(
                buttonEnable = _recreateUiState.value.rewritingNewPassword == password
            )
        }
        return _recreateUiState.value.buttonEnable &&
                _recreateUiState.value.verificationCode.length == 5 &&
                password.length >= 8
    }


}