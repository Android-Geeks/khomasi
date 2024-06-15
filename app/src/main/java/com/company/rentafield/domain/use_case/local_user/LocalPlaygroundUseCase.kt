package com.company.rentafield.domain.use_case.local_user

import com.company.rentafield.domain.use_case.local_playground.GetPlaygroundName
import com.company.rentafield.domain.use_case.local_playground.GetPlaygroundPrice
import com.company.rentafield.domain.use_case.local_playground.SavePlaygroundName
import com.company.rentafield.domain.use_case.local_playground.SavePlaygroundPrice

data class LocalPlaygroundUseCase(
    val savePlaygroundName: SavePlaygroundName,
    val getPlaygroundName: GetPlaygroundName,
    val savePlaygroundPrice: SavePlaygroundPrice,
    val getPlaygroundPrice: GetPlaygroundPrice,
)
