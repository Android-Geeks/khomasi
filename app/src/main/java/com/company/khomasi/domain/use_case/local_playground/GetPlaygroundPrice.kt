package com.company.khomasi.domain.use_case.local_playground

import com.company.khomasi.domain.repository.LocalPlaygroundRepository

class GetPlaygroundPrice(
    private val localPlaygroundUseCase: LocalPlaygroundRepository
) {
    operator fun invoke() = localPlaygroundUseCase.getPlaygroundPrice()
}