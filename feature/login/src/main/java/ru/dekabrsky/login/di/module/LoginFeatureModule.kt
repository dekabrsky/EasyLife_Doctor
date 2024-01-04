package ru.dekabrsky.login.di.module

import ru.dekabrsky.feature.loginCommon.data.mapper.LoginDataMapper
import ru.dekabrsky.feature.loginCommon.domain.interactor.LoginInteractor
import ru.dekabrsky.login.data.api.LoginApi
import ru.dekabrsky.login.data.mapper.LoginDataMapperImpl
import ru.dekabrsky.login.di.provider.LoginApiProvider
import ru.dekabrsky.login.domain.interactor.LoginInteractorImpl
import toothpick.config.Module

class LoginFeatureModule : Module() {
    init {
        bind(LoginApi::class.java).toProvider(LoginApiProvider::class.java)
        bind(LoginInteractor::class.java).to(LoginInteractorImpl::class.java)
        bind(LoginDataMapper::class.java).to(LoginDataMapperImpl::class.java)
    }
}