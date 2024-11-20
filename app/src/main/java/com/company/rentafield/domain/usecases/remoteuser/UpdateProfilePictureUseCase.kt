package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.user.RemoteUserRepository
import okhttp3.MultipartBody

class UpdateProfilePictureUseCase(
    private val remoteUserRepository: RemoteUserRepository
) {
    suspend operator fun invoke(
        token: String,
        userId: String,
        image: MultipartBody.Part
    ) = remoteUserRepository.uploadProfilePicture(token, userId, image)
}