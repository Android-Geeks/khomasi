package com.company.rentafield.domain.usecases.remoteuser

import com.company.rentafield.data.repositories.remoteuser.playground.RemoteUserPlayground

class GetSpecificPlaygroundUseCase(
    private val remoteUserPlayground: RemoteUserPlayground
) {
    suspend operator fun invoke(token: String, id: Int) =
        remoteUserPlayground.getSpecificPlayground(token, id)
}