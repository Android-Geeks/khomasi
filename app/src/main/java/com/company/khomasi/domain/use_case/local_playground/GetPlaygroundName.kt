package com.company.khomasi.domain.use_case.local_playground

import com.company.khomasi.domain.repository.LocalPlaygroundRepository


class GetPlaygroundName(
    private val localPlaygroundUseCase: LocalPlaygroundRepository
) {
    operator fun invoke() = localPlaygroundUseCase.getPlaygroundName()
}