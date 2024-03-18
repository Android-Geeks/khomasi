package com.company.khomasi.domain.use_case.remote_user

data class RemoteUserUseCase(
    val getPlaygroundsUseCase: GetPlaygroundsUseCase,
    val getUserBookingsUseCase : GetUserBookingsUseCase,
    val deleteUserFavoriteUseCase : DeleteUserFavouriteUseCase,
    val getUserFavoritePlaygroundsUseCase : GetUserFavoritePlaygroundsUseCase,
    val userFavouriteUseCase: UserFavouriteUseCase
)

