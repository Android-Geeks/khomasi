package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserRepository
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