package com.company.rentafield.presentation.screens.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.ai.AiResponse
import com.company.rentafield.domain.repository.RemoteAiRepository
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val remoteAiRepository: RemoteAiRepository,
    localUserUseCases: LocalUserUseCases
) : ViewModel() {
    private val _notificationState: MutableStateFlow<DataState<AiResponse>> =
        MutableStateFlow(DataState.Empty)
    val notificationState: StateFlow<DataState<AiResponse>> = _notificationState

    private val _localUser = localUserUseCases.getLocalUser().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalUser()
    )
    val localUser: StateFlow<LocalUser> = _localUser

    fun getAiResult() {
        viewModelScope.launch(IO) {
            remoteAiRepository.getAiResults(_localUser.value.userID ?: "").collect { aiResponse ->
                Log.d("notificationState", "user id is ${_localUser.value.userID}")
                _notificationState.value = aiResponse
            }
        }
    }
}