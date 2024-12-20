package com.company.rentafield.presentation.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.usecases.entry.AppEntryUseCases
import com.company.rentafield.presentation.navigation.components.Screens
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
                Screens.RentafieldNavigation -> _startDestination.value =
                    Screens.RentafieldNavigation.route

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