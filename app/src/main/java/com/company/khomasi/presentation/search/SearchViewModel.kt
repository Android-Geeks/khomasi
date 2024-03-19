package com.company.khomasi.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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

    private var localUser = LocalUser()

    init {
        getSearchHistory()
        getLocalUser()
        if (localUser.token != null && localUser.userID != null) {
            getPlaygrounds(localUser.token!!, localUser.userID!!)
        } else {
            Log.d("SearchViewModel", "token or userID is null")
        }
    }

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val searchResults: StateFlow<List<Playground>> = _searchQuery
        .combine(_playgrounds) { searchQuery, playgroundsResponse ->
            Log.d("SearchViewModel", "playground: $playgroundsResponse")
            when (playgroundsResponse) {
                is DataState.Success -> {
                    val playgrounds = playgroundsResponse.data.playgrounds
                    when {
                        searchQuery.isNotEmpty() -> {
                            val fields = playgrounds.filter { playground ->
                                playground.name.contains(searchQuery, ignoreCase = true)
                            }
                            _uiState.value =
                                _uiState.value.copy(playgroundResults = fields.sortedBy { it.feesForHour })
                            fields
                        }

                        else -> playgrounds
                    }
                }

                else -> emptyList()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSearchFilterChanged(filter: SearchFilter) {
        _uiState.value = _uiState.value.copy(
            searchFilter = filter,
            playgroundResults = when (filter) {
                SearchFilter.LowestPrice -> _uiState.value.playgroundResults.sortedBy { it.feesForHour }
                SearchFilter.Rating -> _uiState.value.playgroundResults.sortedByDescending { it.rating }
                SearchFilter.Nearest -> _uiState.value.playgroundResults.sortedBy { it.distance }
                SearchFilter.Bookable -> _uiState.value.playgroundResults.filter { it.isBookable }
            }
        )
    }

    fun onSearchQuerySubmitted(query: String) {
        if (query.isNotEmpty() && !_uiState.value.searchHistory.contains(query))
            _uiState.value =
                _uiState.value.copy(searchHistory = _uiState.value.searchHistory + query)
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

    fun onBackPage() {
        _uiState.value = _uiState.value.copy(page = 1)
    }

    fun onNextPage() {
        _uiState.value = _uiState.value.copy(page = 2)
    }

    private fun getPlaygrounds(token: String, userId: String) {
        viewModelScope.launch {
            remoteUserUseCase.getPlaygroundsUseCase("Bearer $token", userId).collect {
                _playgrounds.value = it
            }
        }
    }

    private fun getLocalUser() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect {
                localUser = it
            }
        }
    }

    private fun getSearchHistory() {
        viewModelScope.launch {
            localUserUseCases.getSearchHistory().collect {
                _uiState.value = _uiState.value.copy(searchHistory = it)
            }
        }
    }
}