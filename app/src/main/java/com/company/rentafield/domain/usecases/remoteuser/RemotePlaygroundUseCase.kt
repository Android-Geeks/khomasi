package com.company.rentafield.domain.usecases.remoteuser

data class RemotePlaygroundUseCase(
    val getFreeTimeSlotsUseCase: GetFreeTimeSlotsUseCase,
    val getPlaygroundReviewsUseCase: GetPlaygroundReviewsUseCase,
    val getFilteredPlaygroundsUseCase: GetFilteredPlaygroundsUseCase,
    val bookingPlaygroundUseCase: BookingPlaygroundUseCase
)
