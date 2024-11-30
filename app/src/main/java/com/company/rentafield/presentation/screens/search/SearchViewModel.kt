package com.company.rentafield.presentation.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val remoteUserUseCases: RemoteUserUseCases,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _playgrounds: MutableStateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgrounds: StateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundsResponse>> =
        _playgrounds

    val localUser = localUserUseCases.getLocalUser().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000),
        com.company.rentafield.domain.models.LocalUser()
    )

    fun getSearchData() {
        viewModelScope.launch(IO) {
            getSearchHistory()
            if (localUser.value.token != null && localUser.value.userID != null) {
                getPlaygrounds(localUser.value.token!!, localUser.value.userID!!)
            } else {
                Log.d("SearchViewModel", "token or userID is null")
            }
        }
    }


    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val searchResults: StateFlow<List<com.company.rentafield.domain.models.playground.Playground>> =
        _searchQuery
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


    private fun getPlaygrounds(token: String, userId: String) {
        viewModelScope.launch {
            remoteUserUseCases.getPlaygroundsUseCase("Bearer $token", userId).collect {
                _playgrounds.value = it
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