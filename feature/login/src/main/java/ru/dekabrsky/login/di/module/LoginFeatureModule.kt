package ru.dekabrsky.login.di.module

import ru.dekabrsky.login.data.api.LoginApi
import ru.dekabrsky.login.di.provider.LoginApiProvider
import toothpick.config.Module

class LoginFeatureModule : Module() {
    init {
        bind(LoginApi::class.java).toProvider(LoginApiProvider::class.java)
    }
}