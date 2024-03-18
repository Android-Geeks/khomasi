package com.company.khomasi.domain.use_case.remote_user

data class RemoteUserUseCase(
    val getPlaygroundsUseCase: GetPlaygroundsUseCase,
    val getSpecificPlaygroundUseCase: GetSpecificPlaygroundUseCase
)
