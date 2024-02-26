package com.company.khomasi.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

) : ViewModel() {
    private val _user = mutableStateOf(UserState())
    val user: State<UserState> = _user

    init {
        clear()
    }

    fun onFirstNameChange(firstName: String) {
        _user.value = _user.value.copy(firstName = firstName)
    }

    fun onLastNameChange(lastName: String) {
        _user.value = _user.value.copy(lastName = lastName)
    }

    fun onEmailChange(email: String) {
        _user.value = _user.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _user.value = _user.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _user.value = _user.value.copy(confirmPassword = confirmPassword)
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _user.value = _user.value.copy(phoneNumber = phoneNumber)
    }

    fun isValidCredentials(email: String, password: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0–9._-]+@[a-z]+\\.+[a-z]+")
        val passwordPattern = Regex("^(?=.*[0–9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
        return emailPattern.matches(email) && passwordPattern.matches(password)
    }

    fun clear() {
        _user.value = UserState()
    }
}