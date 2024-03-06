package com.company.khomasi.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserLoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MockLoginViewModel : ViewModel() {
    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

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

    fun onLoginSuccess() {
        // Simulate saving login state for testing
        viewModelScope.launch {
            // Simulate saving isLogin state
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

    fun isValidEmailAndPassword(email: String, password: String): Boolean {
        // Simulate email and password validation for testing
        return true // Return true for testing purposes
    }
}
