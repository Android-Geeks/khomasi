package com.company.khomasi.presentation.ui.screens.otpScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OtpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState : StateFlow<OtpUiState> = _uiState.asStateFlow()

    var smsCode by mutableStateOf(_uiState.value.code)
        private set
    fun updateSmsCode(newCode: String) {
        smsCode = newCode
    }
    fun logIn(){

    }
    fun resendCode(){

    }

}