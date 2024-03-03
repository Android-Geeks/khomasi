package com.company.khomasi.presentation.resetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.VerificationResponse
import com.company.khomasi.domain.use_case.auth.AuthUseCases
import com.company.khomasi.utils.CheckInputValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
) : ViewModel() {

    private val _recreateUiState = MutableStateFlow(ResetPasswordUiState())
    val recreateUiState: StateFlow<ResetPasswordUiState> = _recreateUiState.asStateFlow()

    private val _verificationRes =
        MutableStateFlow<DataState<VerificationResponse>>(DataState.Loading)
    val verificationRes: StateFlow<DataState<VerificationResponse>> = _verificationRes

    private val _recoverResponse =
        MutableStateFlow<DataState<String>>(DataState.Loading)
    val recoverResponse: StateFlow<DataState<String>> = _recoverResponse

    fun onUserEmailChange(email: String) {
        _recreateUiState.update {
            it.copy(
                userEmail = email
            )
        }
    }

    fun onClickButtonScreen1() {
        viewModelScope.launch {
            authUseCases.getVerificationCodeUseCase(_recreateUiState.value.userEmail)
                .collect {
                    _verificationRes.value = it
                }
        }
    }

    //------------------------------------------------------------------------------------------------//
    fun onEnteringVerificationCode(code: String) {
        _recreateUiState.update {
            it.copy(
                enteredVerificationCode = code
            )
        }
    }

    fun verifyVerificationCode(code: String) {
        _recreateUiState.let {
            it.update { currentState ->
                currentState.copy(
                    isCodeTrue = currentState.enteredVerificationCode == code
                )
            }
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
                rewritingNewPassword = password
            )
        }
    }

    fun checkPasswordMatching() {
        _recreateUiState.let {
            it.update { currentState ->
                currentState.copy(
                    isTwoPassEquals = currentState.newPassword == currentState.rewritingNewPassword
                            && currentState.rewritingNewPassword.isNotEmpty()
                )
            }
        }
    }

    fun checkValidation(): Boolean {
        _recreateUiState.update {
            it.copy(
                buttonEnable2 = _recreateUiState.value.isTwoPassEquals
            )
        }
        return _recreateUiState.value.newPassword == _recreateUiState.value.rewritingNewPassword
                && CheckInputValidation.isPasswordValid(_recreateUiState.value.newPassword)
    }

    fun onButtonClickedScreen2() {
        if (_recreateUiState.value.buttonEnable2 && _recreateUiState.value.isCodeTrue) {
            viewModelScope.launch {
                authUseCases.recoverAccountUseCase(
                    _recreateUiState.value.userEmail,
                    _recreateUiState.value.enteredVerificationCode,
                    _recreateUiState.value.rewritingNewPassword
                ).collect {
                    _recoverResponse.value = it
                }
            }
        }
    }
}