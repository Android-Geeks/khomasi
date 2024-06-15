package com.company.rentafield.presentation.otpScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.VerificationResponse
import com.company.rentafield.domain.use_case.auth.AuthUseCases
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OtpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState

    private val _otpState: MutableStateFlow<DataState<VerificationResponse>> =
        MutableStateFlow(DataState.Empty)

    val otpState: StateFlow<DataState<VerificationResponse>> = _otpState

    private val _confirmEmailState: MutableStateFlow<DataState<MessageResponse>> =
        MutableStateFlow(DataState.Empty)
    val confirmEmailState: StateFlow<DataState<MessageResponse>> = _confirmEmailState

    fun getRegisterOtp() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { localUser ->
                _uiState.value = _uiState.value.copy(email = localUser.email ?: "")
                _otpState.value = DataState.Success(
                    VerificationResponse(
                        code = localUser.otpCode ?: 0,
                        email = localUser.email ?: "",
                        message = "Confirmation Code Has Been Sent"
                    )
                )
            }
        }
    }


    fun updateSmsCode(newCode: String) {
        _uiState.value = _uiState.value.copy(
            code = newCode,
        )
    }

    fun resendCode() {
        _otpState.value = DataState.Loading
        viewModelScope.launch {
            authUseCases.getVerificationCodeUseCase(email = _uiState.value.email).collect {
                _otpState.value = it
                Log.d("OtpViewModel", "resendCode: $it")
            }
        }
    }

    fun confirmEmail() {
        _confirmEmailState.value = DataState.Loading
        viewModelScope.launch {
            authUseCases.confirmEmailUseCase(
                email = _uiState.value.email,
                code = _uiState.value.code
            ).collect {
                _confirmEmailState.value = it
            }
        }
    }

    private var timerJob: Job? = null
    fun startTimer(time: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (i in time downTo 0) {
                _uiState.value = _uiState.value.copy(timer = i)
                delay(1000L)
            }
        }
    }

    fun resetTimer(time: Int) {
        _uiState.value = _uiState.value.copy(timer = time)
        startTimer(time)
    }
}
