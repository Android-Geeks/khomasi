package com.company.khomasi.presentation.ui.screens.otpScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OtpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState : StateFlow<OtpUiState> = _uiState.asStateFlow()


    fun logIn(){

    }
    fun resendCode(){

    }

}