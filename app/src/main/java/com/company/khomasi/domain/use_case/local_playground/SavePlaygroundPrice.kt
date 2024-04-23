package com.company.khomasi.domain.use_case.local_playground

import com.company.khomasi.domain.repository.LocalPlaygroundRepository

class SavePlaygroundPrice(
    private val localPlaygroundUseCase: LocalPlaygroundRepository
) {
    suspend operator fun invoke(price: Int) = localPlaygroundUseCase.savePlaygroundPrice(price)
}