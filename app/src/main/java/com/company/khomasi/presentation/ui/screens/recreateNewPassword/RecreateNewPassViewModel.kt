package com.company.khomasi.presentation.ui.screens.recreateNewPassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecreateNewPassViewModel : ViewModel() {
    private val _recreateUiState = MutableStateFlow(RecreateNewPassUiState())
    val recreateUiState : StateFlow<RecreateNewPassUiState> = _recreateUiState.asStateFlow()



    fun verifyVerificationCode(code : String) /*: Boolean*/{
        _recreateUiState.update {
            it.copy(
                verificationCode = code
            )
        }
//        return code == _recreateUiState.value.verificationCode
    }

    fun onEnteringPassword(password : String){
        _recreateUiState.update {
            it.copy(
                newPassword = password
            )
        }
    }

    fun onReTypingPassword(password : String){
        _recreateUiState.update {
            it.copy(
                rewritingNewPassword = password,
                buttonEnable = _recreateUiState.value.rewritingNewPassword == password
            )
        }
    }


}