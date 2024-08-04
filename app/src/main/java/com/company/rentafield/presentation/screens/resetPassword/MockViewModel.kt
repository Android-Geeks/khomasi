package com.company.rentafield.presentation.screens.resetPassword

import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.auth.VerificationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockResetPasswordViewModel : ViewModel() {

    private val _resetUiState = MutableStateFlow(ResetPasswordUiState())
    val resetUiState: StateFlow<ResetPasswordUiState> = _resetUiState

    private val _verificationRes =
        MutableStateFlow<DataState<VerificationResponse>>(DataState.Empty)
    val verificationRes: StateFlow<DataState<VerificationResponse>> = _verificationRes

    private val _recoverResponse = MutableStateFlow<DataState<MessageResponse>>(DataState.Empty)
    val recoverResponse: StateFlow<DataState<MessageResponse>> = _recoverResponse

    fun onUserEmailChange(email: String) {
    }

    fun onClickButtonScreen1() {
    }

    fun onEnteringVerificationCode(code: String) {
    }

    fun verifyVerificationCode(code: String) {
    }

    fun onEnteringPassword(password: String) {
    }

    fun onReTypingPassword(password: String) {
    }

    fun onButtonClickedScreen2() {
    }

    fun onBack() {
    }

    fun onNextClick() {
    }
}
