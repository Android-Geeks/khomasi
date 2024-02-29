package com.company.khomasi.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.UserLoginResponse
import com.company.khomasi.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasi.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _x = MutableStateFlow<DataState<UserLoginResponse>>(DataState.Loading)
    val x: StateFlow<DataState<UserLoginResponse>> = _x

    fun onSkipClick() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }

    init {
        test()
    }

    private fun test() {
        viewModelScope.launch {
            authUseCases.loginUseCase("us@g.com", "string1234567").collect {
                _x.value = it
            }
        }
    }

}