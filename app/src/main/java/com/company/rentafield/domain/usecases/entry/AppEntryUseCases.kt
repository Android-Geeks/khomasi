package com.company.rentafield.domain.usecases.entry

data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry,
    val saveIsLogin: SaveIsLogin
)