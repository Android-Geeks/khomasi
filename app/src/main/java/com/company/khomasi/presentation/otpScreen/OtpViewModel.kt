package com.company.khomasi.presentation.otpScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState.asStateFlow()

    fun updateSmsCode(newCode: String) {
        if (_uiState.value.code == newCode) {
            _uiState.value = _uiState.value.copy(
                code = newCode,
                isCodeCorrect = true
            )
        } else {
            _uiState.value = _uiState.value.copy(
                isCodeCorrect = false
            )
        }
    }
    fun login() {

    }
    fun resendCode():String {
        return _uiState.value.code
    }

}