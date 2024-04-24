package com.company.khomasi.domain.use_case.remote_user

import com.company.khomasi.domain.repository.RemoteUserRepository
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