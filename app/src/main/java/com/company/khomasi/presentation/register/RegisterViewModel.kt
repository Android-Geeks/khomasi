package com.company.khomasi.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserDetails
import com.company.khomasi.domain.model.UserRegisterResponse
import com.company.khomasi.domain.use_case.auth.AuthUseCases
import com.company.khomasi.utils.CheckInputValidation
import com.company.khomasi.utils.ExchangeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

    private val _registerState: MutableStateFlow<DataState<UserRegisterResponse>> =
        MutableStateFlow(DataState.Empty)
    val registerState: StateFlow<DataState<UserRegisterResponse>> = _registerState

    fun onRegister() {
        viewModelScope.launch {
            authUseCases.registerUseCase(
                UserDetails(
                    firstName = _uiState.value.firstName,
                    lastName = _uiState.value.lastName,
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    phoneNumber = _uiState.value.phoneNumber,
                    country = "Egypt",
                    city = "Tanta",
                    longitude = 0.0f,
                    latitude = 0.0f,
                )
            ).collect {
                _registerState.value = it
                if (it is DataState.Success) {
                    ExchangeData.email.set(it.data.email)
                    ExchangeData.otp.set(it.data.code)
                }
            }
        }
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
        val isFirstNameValid = CheckInputValidation.isFirstNameValid(firstName)

        val isLastNameValid = CheckInputValidation.isLastNameValid(lastName)

        val isPhoneNumberValid = CheckInputValidation.isPhoneNumberValid(phoneNumber)
         _uiState.value = _uiState.value.copy(validating1 = true)

        return isFirstNameValid && isLastNameValid && isPhoneNumberValid
    }

    fun isValidEmailAndPassword(email: String, password: String): Boolean {
        val isEmailValid = CheckInputValidation.isEmailValid(email)
        val isPasswordValid = CheckInputValidation.isPasswordValid(password)
        _uiState.value = _uiState.value.copy(validating2 = true)
        return isEmailValid && isPasswordValid && _uiState.value.password == _uiState.value.confirmPassword
    }

    fun onBack() {
        _uiState.value = _uiState.value.copy(page = _uiState.value.page - 1)
    }

    fun onNextClick() {
        _uiState.value = _uiState.value.copy(page = _uiState.value.page + 1)
    }
}