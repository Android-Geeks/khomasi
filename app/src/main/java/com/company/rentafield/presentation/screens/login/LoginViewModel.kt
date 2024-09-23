package com.company.rentafield.presentation.screens.login

import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.auth.UserLoginResponse
import com.company.rentafield.domain.use_case.app_entry.AppEntryUseCases
import com.company.rentafield.domain.use_case.auth.AuthUseCases
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import com.company.rentafield.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val authUseCases: AuthUseCases,
    private val localUserUseCases: LocalUserUseCases
) : BaseViewModel<LoginReducer.State, LoginReducer.Event, LoginReducer.Effect>(
    initialState = LoginReducer.initial(),
    reducer = LoginReducer()
) {
    fun login() {
        viewModelScope.launch(IO) {
            if (!isValidEmailAndPassword(state.value.email, state.value.password)) {
                sendEffect(LoginReducer.Effect.Error.InvalidEmailOrPassword)
                return@launch
            }
            sendEvent(LoginReducer.Event.UpdateLoading(true))
            authUseCases.loginUseCase(
                state.value.email,
                state.value.password
            ).collect {
                when (it) {
                    is DataState.Success -> {
                        sendEvent(LoginReducer.Event.UpdateLoading(false))
                        if (it.data.role == "User") {
                            onLoginSuccess(it.data)
                        } else {
                            sendEffect(LoginReducer.Effect.Error.UserLoginOnly)
                        }
                    }

                    is DataState.Error -> {
                        sendEvent(LoginReducer.Event.UpdateLoading(false))
                        sendEffect(
                            when (it.code) {
                                404 -> LoginReducer.Effect.Error.UserNotFound
                                400 -> {
                                    if (it.message == "Invalid Password.") {
                                        LoginReducer.Effect.Error.InvalidPassword
                                    } else {
                                        LoginReducer.Effect.Error.EmailNotConfirmed
                                    }
                                }

                                0 -> LoginReducer.Effect.Error.UserLoginOnly

                                else -> LoginReducer.Effect.Error.Unknown
                            }
                        )
                    }

                    DataState.Empty -> Unit
                    DataState.Loading -> Unit
                }
            }
        }
    }

    private fun onLoginSuccess(loginData: UserLoginResponse) {
        viewModelScope.launch(IO) {
            localUserUseCases.saveLocalUser(
                LocalUser(
                    userID = loginData.userLoginData.userID,
                    token = loginData.token,
                    email = loginData.userLoginData.email,
                    firstName = loginData.userLoginData.firstName,
                    lastName = loginData.userLoginData.lastName,
                    phoneNumber = loginData.userLoginData.phoneNumber,
                    city = loginData.userLoginData.city,
                    country = loginData.userLoginData.country,
                    latitude = loginData.userLoginData.latitude,
                    rating = loginData.userLoginData.rating,
                    coins = loginData.userLoginData.coins
                )
            )
            appEntryUseCases.saveIsLogin(true)
        }
    }

    private fun isValidEmailAndPassword(email: String, password: String): Boolean {
        // Use a regular expression to validate email format
        val isEmailValid =
            email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

        // Password should have at least 12 characters, a number, a symbol, and no spaces
        val isPasswordValid =
            password.matches(Regex("^(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+\$).{12,}\$"))

        return isEmailValid && isPasswordValid
    }

    fun verifyEmail() {
        viewModelScope.launch(IO) {
            authUseCases.getVerificationCodeUseCase(state.value.email).collect {
                if (it is DataState.Success) {
                    localUserUseCases.saveLocalUser(
                        LocalUser(
                            email = state.value.email,
                            otpCode = it.data.code
                        )
                    )
                }
            }
        }
    }
}
