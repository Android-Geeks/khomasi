package com.company.khomasi.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.Playground
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MockFavViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState(userId = "")) // Initialize with an empty userId
    val uiState: StateFlow<FavouriteUiState> = _uiState

    private val _favouritePlaygroundsState = MutableStateFlow<DataState<FavouritePlaygroundResponse>>(DataState.Empty)
    val favouritePlaygroundsState: StateFlow<DataState<FavouritePlaygroundResponse>> = _favouritePlaygroundsState



    fun fetchUserFavoritePlaygrounds() {
        viewModelScope.launch {
            _uiState.value = _uiState.value
           
                val mockResponse = FavouritePlaygroundResponse(
                    playgrounds = listOf(
                        Playground(1, "Playground 1", "Address 1", 4.5, true, 5, 10.0, true,null),
                        Playground(2, "Playground 2", "Address 2", 4.0, true, 7, 8.0, true,null)
                    ),
                    playgroundCount = 2
                )
                _favouritePlaygroundsState.value = DataState.Success(mockResponse)

        }
    }


    fun removeFromFavorites(playgroundId: String) {
    }
}
