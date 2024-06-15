package com.company.rentafield.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.UserRegisterData
import com.company.rentafield.domain.model.UserRegisterResponse
import com.company.rentafield.domain.use_case.auth.AuthUseCases
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import com.company.rentafield.presentation.components.LatandLong
import com.company.rentafield.utils.CheckInputValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _registerState: MutableStateFlow<DataState<UserRegisterResponse>> =
        MutableStateFlow(DataState.Empty)
    val registerState: StateFlow<DataState<UserRegisterResponse>> = _registerState

    fun onRegister() {
        viewModelScope.launch {
            authUseCases.registerUseCase(
                UserRegisterData(
                    firstName = _uiState.value.firstName,
                    lastName = _uiState.value.lastName,
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    phoneNumber = _uiState.value.phoneNumber,
                    country = "Egypt",
                    city = "Tanta",
                    longitude = if (_uiState.value.longitude == 0.0) 31.000376 else _uiState.value.longitude,  // Tanta Coordinates
                    latitude = if (_uiState.value.latitude == 0.0) 30.786509 else _uiState.value.latitude,
                )
            ).collect {
                _registerState.value = it
                if (it is DataState.Success) {
                    localUserUseCases.saveLocalUser(
                        LocalUser(
                            email = it.data.email,
                            otpCode = it.data.code
                        )
                    )
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

    fun updateLocation(locationCoordinates: LatandLong) {
        _uiState.value = _uiState.value.copy(
            latitude = locationCoordinates.latitude,
            longitude = locationCoordinates.longitude,
        )
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
}