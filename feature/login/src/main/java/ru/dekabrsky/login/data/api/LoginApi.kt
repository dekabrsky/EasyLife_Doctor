package ru.dekabrsky.login.data.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.dekabrsky.login.data.model.CredentialsRequest
import ru.dekabrsky.login.data.model.RegistrationRequest
import ru.dekabrsky.feature.loginCommon.data.model.UserInfoResponse
import ru.dekabrsky.login.data.model.LoginTokenResponse
import ru.dekabrsky.login.data.model.LogoutRequest
import ru.dekabrsky.login.data.model.RefreshRequest

interface LoginApi {
    @POST("auth/login")
    fun login(@Body credentialsRequest: CredentialsRequest): Single<LoginTokenResponse>

    @POST("auth/logout")
    fun logout(@Body logoutRequest: LogoutRequest): Completable

    @POST("auth/user")
    fun registration(@Body regRequest: RegistrationRequest): Single<LoginTokenResponse>

    @POST("auth/refresh")
    fun refresh(@Body refreshRequest: RefreshRequest): Single<LoginTokenResponse>

    @GET("users/current")
    fun getCurrentUser():  Single<UserInfoResponse>
}