package com.company.khomasi.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VM @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase
) : ViewModel() {

    private val _registerState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val registerState: StateFlow<DataState<PlaygroundsResponse>> = _registerState

    init {
        viewModelScope.launch {
            remoteUserUseCase.getPlaygroundsUseCase(
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoidXNlMjJyQGV4YW1wbGUuY29tIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiVXNlciIsImV4cCI6MTcxMzEwMDIxMSwiaXNzIjoiV2ViQVBJRGVtbyIsImF1ZCI6IldlYkFQSURlbW8ifQ.uEZAWVuUrDjRk3neC6nhA0AgCoQvQqtC2WiQ1NYclto",
                "771273d4-e626-42ba-9b43-99909fec6f8d"
            ).collect {
                _registerState.value = it
            }
        }
    }
}