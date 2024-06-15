package com.company.rentafield.domain.use_case.local_playground

import com.company.rentafield.domain.repository.LocalPlaygroundRepository


class GetPlaygroundName(
    private val localPlaygroundUseCase: LocalPlaygroundRepository
) {
    operator fun invoke() = localPlaygroundUseCase.getPlaygroundName()
}