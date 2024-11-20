package com.company.rentafield.domain.usecases.auth

data class AuthUseCases(
    val confirmEmailUseCase: ConfirmEmailUseCase,
    val registerUseCase: RegisterUseCase,
    val loginUseCase: LoginUseCase,
    val getVerificationCodeUseCase: GetVerificationCodeUseCase,
    val recoverAccountUseCase: RecoverAccountUseCase
)
