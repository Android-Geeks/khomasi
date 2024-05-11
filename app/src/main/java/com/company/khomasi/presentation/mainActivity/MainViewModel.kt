package com.company.khomasi.presentation.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasi.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    private val _splashCondition = MutableStateFlow(true)
    val splashCondition: StateFlow<Boolean> = _splashCondition

    private val _startDestination = MutableStateFlow(Screens.AppStartNavigation.route)
    val startDestination: StateFlow<String> = _startDestination

    init {
        appEntryUseCases.readAppEntry().onEach { startingRoute ->
            when (startingRoute) {
                Screens.KhomasiNavigation -> _startDestination.value =
                    Screens.KhomasiNavigation.route

                Screens.AuthNavigation -> _startDestination.value = Screens.AuthNavigation.route
                Screens.AppStartNavigation -> _startDestination.value =
                    Screens.AppStartNavigation.route

                else -> {
                    _startDestination.value = Screens.AppStartNavigation.route
                }
            }
            delay(200)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}