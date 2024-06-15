package com.company.rentafield.domain.use_case.local_playground

import com.company.rentafield.domain.repository.LocalPlaygroundRepository

class GetPlaygroundPrice(
    private val localPlaygroundUseCase: LocalPlaygroundRepository
) {
    operator fun invoke() = localPlaygroundUseCase.getPlaygroundPrice()
}