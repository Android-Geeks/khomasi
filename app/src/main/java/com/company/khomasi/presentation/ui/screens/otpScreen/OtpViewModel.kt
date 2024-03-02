package com.company.khomasi.presentation.ui.screens.otpScreen

import android.util.Log
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
        _uiState.value = _uiState.value.copy(code = newCode)
        Log.d("OtpViewModel", "updateSmsCode: ${_uiState.value.code}")
    }

    fun login() {

    }

    fun resendCode() {

    }

}