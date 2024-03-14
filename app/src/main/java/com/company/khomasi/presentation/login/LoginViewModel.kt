package com.company.khomasi.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasi.domain.use_case.auth.AuthUseCases
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
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
        viewModelScope.launch {
            _loginState.value = DataState.Loading
            authUseCases.loginUseCase(
                _uiState.value.email,
                _uiState.value.password
            ).collect {
                _loginState.value = it
                if (it is DataState.Success) {
                    onLoginSuccess()
                    localUserUseCases.saveLocalUser(
                        LocalUser(
                            userID = it.data.userLoginData.userID,
                            token = it.data.token,
                            email = it.data.userLoginData.email,
                            firstName = it.data.userLoginData.firstName,
                            lastName = it.data.userLoginData.lastName,
                            phoneNumber = it.data.userLoginData.phoneNumber,
                            city = it.data.userLoginData.city,
                            country = it.data.userLoginData.country,
                            latitude = it.data.userLoginData.latitude,
                            longitude = it.data.userLoginData.longitude,
                            rating = it.data.userLoginData.rating,
                            coins = it.data.userLoginData.coins
                        )
                    )
                }
            }
        }
    }

    fun onLoginSuccess() {
        viewModelScope.launch {
            appEntryUseCases.saveIsLogin()
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

