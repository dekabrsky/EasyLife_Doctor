package ru.dekabrsky.login.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.login.data.api.LoginApi
import ru.dekabrsky.login.data.mapper.LoginCredentialsToRequestMapper
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginApi,
    private val mapper: LoginCredentialsToRequestMapper
) { // тут решил пропустить domain-слой
    fun login(login: String, password: String): Completable = api.login(mapper.mapCredentials(login, password))
}