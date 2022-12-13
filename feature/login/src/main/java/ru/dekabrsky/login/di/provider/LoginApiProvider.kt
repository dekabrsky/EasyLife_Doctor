package ru.dekabrsky.login.di.provider

import retrofit2.Retrofit
import ru.dekabrsky.login.data.api.LoginApi
import javax.inject.Inject
import javax.inject.Provider

class LoginApiProvider @Inject constructor(private val retrofit: Retrofit) : Provider<LoginApi> {

    override fun get(): LoginApi = retrofit.create(LoginApi::class.java)

}