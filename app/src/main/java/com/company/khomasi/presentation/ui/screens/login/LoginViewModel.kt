package com.company.khomasi.presentation.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updatePassword(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun login() {
        viewModelScope.launch {

        }
    }

    fun createAccount() {

    }


    fun logo() {
    }

    fun privacyAndPolicy() {
        TODO("Not yet implemented")
    }

    fun helpAndSupport() {
        TODO("Not yet implemented")
    }

    private fun isValidCredentials(email: String, password: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0–9._-]+@[a-z]+\\.+[a-z]+")
        val passwordPattern = Regex("^(?=.*[0–9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{12,}$")
        return emailPattern.matches(email) && passwordPattern.matches(password)
    }

}
