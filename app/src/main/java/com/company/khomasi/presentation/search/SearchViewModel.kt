package com.company.khomasi.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _playgrounds: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgrounds: StateFlow<DataState<PlaygroundsResponse>> = _playgrounds

    private var localUser = LocalUser()

    init {
        viewModelScope.launch {
            getSearchHistory()
            localUserUseCases.getLocalUser().collect {
                localUser = it
                if (localUser.token != null && localUser.userID != null)
                    getPlaygrounds(localUser.token!!, localUser.userID!!)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun onSearchFilterChanged(filter: SearchFilter) {
        _uiState.value = _uiState.value.copy(searchFilter = filter)
    }

    fun onSearchQuerySubmitted(query: String) {
        _uiState.value = _uiState.value.copy(searchHistory = _uiState.value.searchHistory + query)
        viewModelScope.launch {
            localUserUseCases.saveSearchHistory(query)
        }
    }

    fun onClickRemoveSearchHistory() {
        _uiState.value = _uiState.value.copy(searchHistory = emptyList())
        viewModelScope.launch {
            localUserUseCases.removeSearchHistory()
        }
    }

    private suspend fun getPlaygrounds(token: String, userId: String) {
        remoteUserUseCase.getPlaygroundsUseCase(token, userId).collect {
            _playgrounds.value = it
        }
    }

    private suspend fun getSearchHistory() {
        localUserUseCases.getSearchHistory().collect {
            _uiState.value = _uiState.value.copy(searchHistory = it)
        }
    }

}