import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import com.company.khomasi.presentation.favorite.FavouriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritePlaygroundsViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState.asStateFlow()

    private val _favouritePlaygroundsState =
        MutableStateFlow<DataState<FavouritePlaygroundResponse>>(DataState.Empty)
    val favouritePlaygroundsState: StateFlow<DataState<FavouritePlaygroundResponse>> =
        _favouritePlaygroundsState

    fun fetchUserFavoritePlaygrounds(userId: String) {
        viewModelScope.launch {
            _favouritePlaygroundsState.value = DataState.Loading
            remoteUserUseCase.getUserFavoritePlaygroundsUseCase(_uiState.value.userId).collect {
                _favouritePlaygroundsState.value = it
            }
        }
    }

    fun addToFavorites(userId: String, playgroundId: String) {
        viewModelScope.launch {
            remoteUserUseCase.userFavouriteUseCase(userId, playgroundId)
            fetchUserFavoritePlaygrounds(userId)
        }
    }

    fun removeFromFavorites(userId: String, playgroundId: String) {
        viewModelScope.launch {
            remoteUserUseCase.deleteUserFavoriteUseCase(userId, playgroundId)
            fetchUserFavoritePlaygrounds(userId)
        }
    }
}
