package com.company.khomasi.presentation.onboarding

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasi.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {
    val x = mutableStateOf("")
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
            x.value = authUseCases.loginUseCase(
                email = "xofimor170@sfpixel.com",
                password = "Ali12345678910",
            ).token
        }
    }

}