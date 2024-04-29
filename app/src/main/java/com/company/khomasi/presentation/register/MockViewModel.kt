package com.company.khomasi.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserRegisterData
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.presentation.components.LatandLong
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MockRegisterViewModel : ViewModel() {

    private val _uiState = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

    private val _registerState: MutableStateFlow<DataState<UserRegisterResponse>> =
        MutableStateFlow(DataState.Empty)
    val registerState: StateFlow<DataState<UserRegisterResponse>> = _registerState

    fun onRegister() {
        viewModelScope.launch {
            val userRegisterData = UserRegisterData(
                firstName = _uiState.value.firstName,
                lastName = _uiState.value.lastName,
                email = _uiState.value.email,
                password = _uiState.value.password,
                phoneNumber = _uiState.value.phoneNumber,
                country = "Egypt",
                city = "Tanta",
                longitude = 0.0,
                latitude = 0.0
            )
            val userRegisterResponse = UserRegisterResponse(
                email = userRegisterData.email,
                code = 1234,
                message = " "
            )
            _registerState.value = DataState.Success(userRegisterResponse)

        }
    }

    fun updateLocation(locationCoordinates: LatandLong) {
        _uiState.value = _uiState.value.copy(
            latitude = locationCoordinates.latitude,
            longitude = locationCoordinates.longitude,
        )
    }

    fun onFirstNameChange(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun onLastNameChange(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = phoneNumber)
    }
    fun isValidNameAndPhoneNumber(
        firstName: String,
        lastName: String,
        phoneNumber: String
    ): Boolean {
        return firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank()
    }

    fun isValidEmailAndPassword(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank() && password == _uiState.value.confirmPassword
    }
}
