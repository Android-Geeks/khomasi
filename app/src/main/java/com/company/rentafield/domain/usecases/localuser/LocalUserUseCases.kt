package com.company.rentafield.domain.usecases.localuser

data class LocalUserUseCases(
    val saveLocalUser: SaveLocalUser,
    val getLocalUser: GetLocalUser,
    val saveSearchHistory: SaveSearchHistory,
    val getSearchHistory: GetSearchHistory,
    val removeSearchHistory: RemoveSearchHistory,
)