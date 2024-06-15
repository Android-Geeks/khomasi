package com.company.rentafield.domain.use_case.local_playground

import com.company.rentafield.domain.repository.LocalPlaygroundRepository


class SavePlaygroundName(
    private val localPlaygroundUseCase: LocalPlaygroundRepository
) {
    suspend operator fun invoke(playgroundName: String) =
        localPlaygroundUseCase.savePlaygroundName(playgroundName)
}