package com.company.rentafield.presentation.screens.resetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.auth.VerificationResponse
import com.company.rentafield.domain.use_case.auth.AuthUseCases
import com.company.rentafield.utils.CheckInputValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
) : ViewModel() {

    private val _resetUiState = MutableStateFlow(ResetPasswordUiState())
    val resetUiState: StateFlow<ResetPasswordUiState> = _resetUiState

    private val _verificationRes =
        MutableStateFlow<DataState<VerificationResponse>>(DataState.Empty)
    val verificationRes: StateFlow<DataState<VerificationResponse>> = _verificationRes

    private val _recoverResponse =
        MutableStateFlow<DataState<MessageResponse>>(DataState.Empty)
    val recoverResponse: StateFlow<DataState<MessageResponse>> = _recoverResponse

    fun onUserEmailChange(email: String) {
        _resetUiState.update {
            it.copy(
                userEmail = email
            )
        }
    }

    fun onCorrectCodeChange(code: String) {
        _resetUiState.update {
            it.copy(
                correctCode = code
            )
        }
    }

    fun onClickButtonScreen1() {
        _resetUiState.value = _resetUiState.value.copy(validating1 = true)

        if (CheckInputValidation.isEmailValid(resetUiState.value.userEmail)) {
            viewModelScope.launch {
                authUseCases.getVerificationCodeUseCase(_resetUiState.value.userEmail)
                    .collect {
                        _verificationRes.value = it
                    }
            }
        }
    }

    //------------------------------------------------------------------------------------------------//
    fun onEnteringVerificationCode(code: String) {
        _resetUiState.update {
            it.copy(
                enteredVerificationCode = code
            )
        }
    }

    fun verifyVerificationCode(code: String) {
        _resetUiState.let {
            it.update { currentState ->
                currentState.copy(
                    isCodeTrue = currentState.enteredVerificationCode == code
                )
            }
        }
    }

    fun onEnteringPassword(password: String) {
        _resetUiState.update {
            it.copy(
                newPassword = password
            )
        }
    }

    fun onReTypingPassword(password: String) {
        _resetUiState.update {
            it.copy(
                rewritingNewPassword = password
            )
        }
    }

    private fun checkValidation(): Boolean {
        _resetUiState.update {
            it.copy(
                buttonEnable2 = _resetUiState.value.newPassword == _resetUiState.value.rewritingNewPassword
                        && _resetUiState.value.rewritingNewPassword.isNotEmpty()
            )
        }
        return _resetUiState.value.newPassword == _resetUiState.value.rewritingNewPassword
                && CheckInputValidation.isPasswordValid(_resetUiState.value.newPassword)
    }

    fun onButtonClickedScreen2() {
        _resetUiState.value = _resetUiState.value.copy(validating2 = true)
        if (checkValidation() && _resetUiState.value.isCodeTrue) {
            viewModelScope.launch {
                authUseCases.recoverAccountUseCase(
                    _resetUiState.value.userEmail,
                    _resetUiState.value.enteredVerificationCode,
                    _resetUiState.value.rewritingNewPassword
                ).collect {
                    _recoverResponse.value = it
                }
            }
        }
    }
}