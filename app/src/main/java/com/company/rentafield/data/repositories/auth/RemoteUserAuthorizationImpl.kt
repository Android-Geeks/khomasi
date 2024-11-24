package com.company.rentafield.data.repositories.auth

import com.company.rentafield.data.services.RetrofitAuthService
import com.company.rentafield.utils.handleApi

class RemoteUserAuthorizationImpl(
    private val retrofitAuthService: RetrofitAuthService
) : RemoteUserAuthorization {

    override suspend fun registerUser(userRegisterData: com.company.rentafield.domain.models.auth.UserRegisterData) =
        handleApi { retrofitAuthService.registerUser(userRegisterData) }

    override suspend fun loginUser(email: String, password: String) =
        handleApi { retrofitAuthService.loginUser(email, password) }

    override suspend fun getVerificationCode(email: String) =
        handleApi { retrofitAuthService.getVerificationCode(email) }

    override suspend fun confirmEmail(email: String, code: String) =
        handleApi { retrofitAuthService.confirmEmail(email, code) }

    override suspend fun recoverAccount(email: String, code: String, newPassword: String) =
        handleApi { retrofitAuthService.recoverAccount(email, code, newPassword) }
}