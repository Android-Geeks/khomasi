package com.company.rentafield.presentation.screens.home.model

import com.company.rentafield.domain.model.playground.Playground
import com.company.rentafield.presentation.screens.home.constants.adsList

data class HomeUiState(
    val userName: String = "",
    val userId: String = "",
    val adList:  List<AdsContent> =adsList,
    val playgrounds: List<Playground> = listOf(),
    val canUploadVideo: Boolean = false,
    val profileImage: String? = null
)
