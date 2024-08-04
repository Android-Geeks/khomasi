package com.company.rentafield.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.auth.UserLoginResponse
import com.company.rentafield.domain.use_case.app_entry.AppEntryUseCases
import com.company.rentafield.domain.use_case.auth.AuthUseCases
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val authUseCases: AuthUseCases,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

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
        viewModelScope.launch {
            _loginState.value = DataState.Loading
            authUseCases.loginUseCase(
                _uiState.value.email,
                _uiState.value.password
            ).collect {
                _loginState.value = it
            }
        }
    }

    fun onLoginSuccess(loginData: DataState.Success<UserLoginResponse>) {
        viewModelScope.launch {
            localUserUseCases.saveLocalUser(
                LocalUser(
                    userID = loginData.data.userLoginData.userID,
                    token = loginData.data.token,
                    email = loginData.data.userLoginData.email,
                    firstName = loginData.data.userLoginData.firstName,
                    lastName = loginData.data.userLoginData.lastName,
                    phoneNumber = loginData.data.userLoginData.phoneNumber,
                    city = loginData.data.userLoginData.city,
                    country = loginData.data.userLoginData.country,
                    latitude = loginData.data.userLoginData.latitude,
                    longitude = loginData.data.userLoginData.longitude,
                    rating = loginData.data.userLoginData.rating,
                    coins = loginData.data.userLoginData.coins
                )
            )
            appEntryUseCases.saveIsLogin(true)
        }
    }

    fun verifyEmail() {
        viewModelScope.launch {
            authUseCases.getVerificationCodeUseCase(_uiState.value.email).collect {
                if (it is DataState.Success) {
                    localUserUseCases.saveLocalUser(
                        LocalUser(
                            email = _uiState.value.email,
                            otpCode = it.data.code
                        )
                    )
                }
            }
        }
    }


    fun loginWithGmail() {

    }

    fun privacyAndPolicy() {

    }

    fun helpAndSupport() {

    }

    fun isValidEmailAndPassword(email: String, password: String): Boolean {
        // Use a regular expression to validate email format
        val isEmailValid = email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

        // Password should have at least 12 characters, a number, a symbol, and no spaces
        val isPasswordValid =
            password.matches(Regex("^(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+\$).{12,}\$"))

        return isEmailValid && isPasswordValid
    }

}

