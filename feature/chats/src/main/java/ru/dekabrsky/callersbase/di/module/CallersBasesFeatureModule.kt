package ru.dekabrsky.callersbase.di.module

import ru.dekabrsky.callersbase.data.api.CallersBaseApi
import ru.dekabrsky.callersbase.di.provider.CallersBaseApiProvider
import ru.dekabrsky.callersbase.domain.interactor.CallersBaseInteractorImpl
import ru.dekabrsky.callersbase_common.domain.interactor.CallersBaseInteractor
import toothpick.config.Module

class CallersBasesFeatureModule: Module() {
    init {
        bind(CallersBaseApi::class.java).toProvider(CallersBaseApiProvider::class.java)
        bind(CallersBaseInteractor::class.java).to(CallersBaseInteractorImpl::class.java)
    }
}