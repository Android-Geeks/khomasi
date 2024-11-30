package com.company.rentafield.presentation.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.usecases.entry.AppEntryUseCases
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCases
import com.company.rentafield.utils.toBase64String
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val remoteUserUseCases: RemoteUserUseCases,
    private val localUserUseCases: LocalUserUseCases,
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    private val _localUser =
        localUserUseCases.getLocalUser()
            .stateIn(
                viewModelScope, SharingStarted.WhileSubscribed(5_000),
                com.company.rentafield.domain.models.LocalUser()
            )
    val localUser: StateFlow<com.company.rentafield.domain.models.LocalUser> = _localUser

    private val _profileUiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState

    fun getProfileImage() {
        viewModelScope.launch {
            remoteUserUseCases.getProfileImageUseCase(
                token = "Bearer ${_profileUiState.value.user.token ?: ""}",
                userId = _profileUiState.value.user.userID ?: ""
            ).collect {
                if (it is DataState.Success) {
                    _profileUiState.value = _profileUiState.value.copy(
                        oldProfileImage = it.data.profilePicture
                    )
                }
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch(IO) {
            appEntryUseCases.saveIsLogin(false)
            localUserUseCases.saveLocalUser(com.company.rentafield.domain.models.LocalUser())
        }
    }

    fun updateUserData(user: com.company.rentafield.domain.models.LocalUser) {
        _profileUiState.value = _profileUiState.value.copy(
            user = user,
        )
    }

    fun onFeedbackCategorySelected(feedbackCategory: FeedbackCategory) {
        _profileUiState.value = _profileUiState.value.copy(feedbackCategory = feedbackCategory)
    }

    fun onFeedbackChanged(feedback: String) {
        _profileUiState.value = _profileUiState.value.copy(feedback = feedback)
    }


    fun onFirstNameChanged(firstName: String) {
        _profileUiState.value = _profileUiState.value.copy(
            user = _profileUiState.value.user.copy(firstName = firstName)
        )
    }

    fun onLastNameChanged(lastName: String) {
        _profileUiState.value = _profileUiState.value.copy(
            user = _profileUiState.value.user.copy(lastName = lastName)
        )
    }

    fun onPhoneChanged(phone: String) {
        _profileUiState.value = _profileUiState.value.copy(
            user = _profileUiState.value.user.copy(phoneNumber = phone)
        )
    }

    fun onChangeProfileImage(image: File) {
        viewModelScope.launch(IO) {
            _profileUiState.value = _profileUiState.value.copy(
                profileImage = image,
            )
        }
    }


    fun onSaveProfile() {
        viewModelScope.launch(IO) {
            if (_profileUiState.value.profileImage != null) {
                _profileUiState.value = _profileUiState.value.copy(
                    oldProfileImage = _profileUiState.value.profileImage?.toBase64String()
                )
            }

            localUserUseCases.saveLocalUser(_profileUiState.value.user)

            remoteUserUseCases.updateUserUseCase(
                token = "Bearer ${_profileUiState.value.user.token ?: ""}",
                userId = _profileUiState.value.user.userID ?: "",
                user = com.company.rentafield.domain.models.user.UserUpdateData(
                    id = _profileUiState.value.user.userID ?: "",
                    firstName = _profileUiState.value.user.firstName ?: "",
                    lastName = _profileUiState.value.user.lastName ?: "",
                    phoneNumber = _profileUiState.value.user.phoneNumber ?: "",
                    city = _profileUiState.value.user.city ?: "",
                    country = _profileUiState.value.user.country ?: "",
                    longitude = _profileUiState.value.user.longitude ?: 0.0,
                    latitude = _profileUiState.value.user.latitude ?: 0.0
                )
            ).collect()


            val imageFile = _profileUiState.value.profileImage
            if (imageFile?.exists() == true) {
                val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body =
                    MultipartBody.Part.createFormData("profilePicture", imageFile.name, requestFile)

                remoteUserUseCases.updateProfilePictureUseCase(
                    token = "Bearer ${_profileUiState.value.user.token ?: ""}",
                    userId = _profileUiState.value.user.userID ?: "",
                    image = body
                ).collect {
                    Log.d("ProfileViewModel", "onSaveProfile: $it")
                }
            } else {
                Log.d("ProfileViewModel", "onSaveProfile: File does not exist")
            }
        }
    }

    fun sendFeedback() {
        viewModelScope.launch(IO) {
            remoteUserUseCases.sendFeedbackUseCase(
                token = "Bearer ${_localUser.value.token ?: ""}",
                feedback = com.company.rentafield.domain.models.user.FeedbackRequest(
                    userId = _localUser.value.userID ?: "",
                    content = _profileUiState.value.feedback,
                    category = _profileUiState.value.feedbackCategory.name
                )
            ).collect()
            _profileUiState.value = _profileUiState.value.copy(
                feedback = "",
                feedbackCategory = FeedbackCategory.Suggestion
            )
        }
    }
}