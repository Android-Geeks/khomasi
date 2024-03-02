package com.company.khomasi.presentation.ui.screens.logInScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LogInUiState())
    val uiState: StateFlow<LogInUiState> = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set
    fun updatePassword(newPassword: String) {
         password = newPassword
       // _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
      //  _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun logIn() {
        viewModelScope.launch {

        }
    }

    fun createAccount() {
        viewModelScope.launch {
        }

    }


    fun logo() {
    }

    fun privacyAndPolicy() {
    }

    fun helpAndSupport() {
    }
    fun isValidCredentials(email: String, password: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0–9._-]+@[a-z]+\\.+[a-z]+")
        val passwordPattern = Regex("^(?=.*[0–9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
        return emailPattern.matches(email) && passwordPattern.matches(password)
    }
}
