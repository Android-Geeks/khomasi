package com.company.rentafield.domain.use_case.remote_user

data class RemoteUserUseCase(
    val getPlaygroundsUseCase: GetPlaygroundsUseCase,
    val getUserBookingsUseCase: GetUserBookingsUseCase,
    val deleteUserFavoriteUseCase: DeleteUserFavouriteUseCase,
    val getUserFavoritePlaygroundsUseCase: GetUserFavoritePlaygroundsUseCase,
    val userFavouriteUseCase: UserFavouriteUseCase,
    val getSpecificPlaygroundUseCase: GetSpecificPlaygroundUseCase,
    val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val cancelBookingUseCase: CancelBookingUseCase,
    val playgroundReviewUseCase: PlaygroundReviewUseCase,
    val sendFeedbackUseCase: SendFeedbackUseCase,
    val getProfileImageUseCase: GetProfileImageUseCase,
    val userDataUseCase: UserDataUseCase
)
