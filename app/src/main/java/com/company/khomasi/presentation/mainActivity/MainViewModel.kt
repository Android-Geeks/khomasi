package com.company.khomasi.presentation.mainActivity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasi.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Routes.KhomasiNavigation.name)
    val startDestination: State<String> = _startDestination

    init {
        appEntryUseCases.readAppEntry().onEach { startFromHome ->
            if (startFromHome)
                _startDestination.value = Routes.KhomasiNavigation.name
            else
                _startDestination.value = Routes.AppStartNavigation.name
            delay(200)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}