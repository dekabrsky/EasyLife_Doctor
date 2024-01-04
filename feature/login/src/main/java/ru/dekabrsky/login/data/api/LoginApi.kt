package ru.dekabrsky.login.data.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.dekabrsky.login.data.model.CredentialsRequest
import ru.dekabrsky.login.data.model.RegistrationRequest
import ru.dekabrsky.feature.loginCommon.data.model.UserInfoResponse

interface LoginApi {
    @POST("users/login")
    fun login(@Body credentialsRequest: CredentialsRequest): Single<UserInfoResponse>

    @POST("users/logout")
    fun logout(): Completable

    @POST("register/user")
    fun registration(@Body regRequest: RegistrationRequest): Single<UserInfoResponse>

    @GET("users/current")
    fun getCurrentUser():  Single<UserInfoResponse>
}