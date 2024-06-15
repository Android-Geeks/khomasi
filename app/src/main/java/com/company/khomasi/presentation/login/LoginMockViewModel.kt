package com.company.khomasi.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserLoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginMockViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _loginState: MutableStateFlow<DataState<UserLoginResponse>> =
        MutableStateFlow(DataState.Empty)
    val loginState: StateFlow<DataState<UserLoginResponse>> = _loginState

    fun updatePassword(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun login() {
        // Simulate a successful login response for testing
        viewModelScope.launch {
            _loginState.value = DataState.Empty
        }
    }

    fun loginWithGmail() {
        // Simulate login with Gmail for testing
    }

    fun privacyAndPolicy() {
        // Simulate privacy and policy action for testing
    }

    fun helpAndSupport() {
        // Simulate help and support action for testing
    }
}
