package ru.dekabrsky.login.data.api

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST
import ru.dekabrsky.login.data.model.CredentialsRequest

interface LoginApi {
    @POST("users/login")
    fun login(@Body credentialsRequest: CredentialsRequest): Completable
}