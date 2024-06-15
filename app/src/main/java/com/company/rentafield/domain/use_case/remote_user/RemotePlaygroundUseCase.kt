package com.company.rentafield.domain.use_case.remote_user

data class RemotePlaygroundUseCase(
    val getFreeTimeSlotsUseCase: GetFreeTimeSlotsUseCase,
    val getPlaygroundReviewsUseCase: GetPlaygroundReviewsUseCase,
    val getFilteredPlaygroundsUseCase: GetFilteredPlaygroundsUseCase,
    val bookingPlaygroundUseCase: BookingPlaygroundUseCase
)
