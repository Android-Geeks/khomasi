package com.company.rentafield.data.repositories.auth

import com.company.rentafield.data.services.RetrofitService
import com.company.rentafield.utils.handleApi

class RemoteUserAuthorizationImpl(
    private val retrofitService: RetrofitService
) : RemoteUserAuthorization {

    override suspend fun registerUser(userRegisterData: com.company.rentafield.domain.models.auth.UserRegisterData) =
        handleApi { retrofitService.registerUser(userRegisterData) }

    override suspend fun loginUser(email: String, password: String) =
        handleApi { retrofitService.loginUser(email, password) }

    override suspend fun getVerificationCode(email: String) =
        handleApi { retrofitService.getVerificationCode(email) }

    override suspend fun confirmEmail(email: String, code: String) =
        handleApi { retrofitService.confirmEmail(email, code) }

    override suspend fun recoverAccount(email: String, code: String, newPassword: String) =
        handleApi { retrofitService.recoverAccount(email, code, newPassword) }
}