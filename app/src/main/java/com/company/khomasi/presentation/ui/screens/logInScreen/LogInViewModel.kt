package com.company.khomasi.presentation.ui.screens.logInScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LogInViewModel :ViewModel() {
    private val _uiState = MutableStateFlow(LogInUiState())
    val uiState : StateFlow<LogInUiState> = _uiState.asStateFlow()


   var email by mutableStateOf("")
       private set

    fun updateEmail(input: String) {
        email = input
    }
    var password by mutableStateOf("")
        private set

    fun updatePassword(input: String) {
        password = input
    }
    fun logIn(){

    }
    fun createAccount(){

    }

    fun logo(){

    }
    fun privacyAndPolicy(){

    }
    fun helpAndSupport(){

    }

}