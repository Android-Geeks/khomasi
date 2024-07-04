package com.company.rentafield.presentation.otp

import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.auth.VerificationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MockOtpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        OtpUiState(
            email = "mocked_email",
            code = "",
            timer = 0
        )
    )
    val uiState: StateFlow<OtpUiState> = _uiState

    private val _otpState: MutableStateFlow<DataState<VerificationResponse>> =
        MutableStateFlow(DataState.Empty)

    val otpState: StateFlow<DataState<VerificationResponse>> = _otpState

    private val _confirmEmailState: MutableStateFlow<DataState<MessageResponse>> =
        MutableStateFlow(DataState.Empty)

    val confirmEmailState: StateFlow<DataState<MessageResponse>> = _confirmEmailState


    fun updateSmsCode(newCode: String) {
    }

    fun resendCode() {
        _otpState.value = DataState.Success(
            VerificationResponse(
                code = 132,
                email = _uiState.value.email,
                message = "Confirmation Code Has Been Sent (Mocked)"
            )
        )
    }

    fun confirmEmail() {
        _confirmEmailState.value = DataState.Success(
            MessageResponse(
                message = "Email Confirmed (Mocked)"
            )
        )
    }

    fun startTimer(time: Int) {
    }

    fun resetTimer(time: Int) {
    }

}
