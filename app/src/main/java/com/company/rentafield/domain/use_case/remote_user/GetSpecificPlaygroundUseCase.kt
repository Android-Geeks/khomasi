package com.company.rentafield.domain.use_case.remote_user

import com.company.rentafield.domain.repository.RemoteUserPlayground

class GetSpecificPlaygroundUseCase(
    private val remoteUserPlayground: RemoteUserPlayground
) {
    suspend operator fun invoke(token: String, id: Int) =
        remoteUserPlayground.getSpecificPlayground(token, id)
}