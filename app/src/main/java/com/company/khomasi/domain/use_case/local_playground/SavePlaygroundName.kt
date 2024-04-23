package com.company.khomasi.domain.use_case.local_playground

import com.company.khomasi.domain.repository.LocalPlaygroundRepository


class SavePlaygroundName(
    private val localPlaygroundUseCase: LocalPlaygroundRepository
) {
    suspend operator fun invoke(playgroundName: String) =
        localPlaygroundUseCase.savePlaygroundName(playgroundName)
}