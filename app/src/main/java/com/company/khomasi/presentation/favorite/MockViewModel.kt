import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.presentation.favorite.FavouriteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MockViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState(userId = "")) // Initialize with an empty userId
    val uiState: StateFlow<FavouriteUiState> = _uiState

    private val _favouritePlaygroundsState = MutableStateFlow<DataState<FavouritePlaygroundResponse>>(DataState.Empty)
    val favouritePlaygroundsState: StateFlow<DataState<FavouritePlaygroundResponse>> = _favouritePlaygroundsState

    fun fetchUserFavoritePlaygrounds(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(userId = userId) // Update userId in the uiState
            _favouritePlaygroundsState.value = DataState.Loading
           
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

    fun addToFavorites(userId: String, playgroundId: String) {
    }

    fun removeFromFavorites(userId: String, playgroundId: String) {
    }
}
