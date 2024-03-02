package com.company.khomasi.presentation.ui.screens.recreateNewPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.VerificationResponse
import com.company.khomasi.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecreateNewPassViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
): ViewModel() {

    private val _recreateUiState = MutableStateFlow(RecreateNewPassUiState())
    val recreateUiState: StateFlow<RecreateNewPassUiState> = _recreateUiState.asStateFlow()

    private val _verificationRes = MutableStateFlow<DataState<VerificationResponse>>(DataState.Loading)
    val verificationRes: StateFlow<DataState<VerificationResponse>> = _verificationRes

    fun onUserEmailChange(email: String) {
        _recreateUiState.update {
            it.copy(
                userEmail = email
            )
        }

    }

    fun onClickButton(){
        viewModelScope.launch {
            authUseCases.getVerificationCodeUseCase(_recreateUiState.value.userEmail)
                .collect {
                    _verificationRes.value = it
                }
        }
    }
    fun onEnteringVerificationCode(code: String) {
        _recreateUiState.update {
            it.copy(
                enteredVerificationCode = code
            )
        }
    }

    fun verifyVerificationCode(code: String) {
        _recreateUiState.update {
            it.copy(
                isCodeTrue = _recreateUiState.value.enteredVerificationCode == code
            )
        }
    }

    fun onEnteringPassword(password: String) {
        _recreateUiState.update {
            it.copy(
                newPassword = password
            )
        }
    }

    fun onReTypingPassword(password: String) {
        _recreateUiState.update {
            it.copy(
                rewritingNewPassword = password,
            )
        }
    }

    fun checkValidation(password: String): Boolean {
        _recreateUiState.update {
            it.copy(
                buttonEnable = _recreateUiState.value.rewritingNewPassword == password
            )
        }
        return _recreateUiState.value.buttonEnable &&
                _recreateUiState.value.enteredVerificationCode.length == 5
//                && password.length >= 8
    }


}