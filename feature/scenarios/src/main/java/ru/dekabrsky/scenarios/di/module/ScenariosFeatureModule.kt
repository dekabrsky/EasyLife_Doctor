package ru.dekabrsky.scenarios.di.module

import ru.dekabrsky.scenarios.data.api.ScenariosApi
import ru.dekabrsky.scenarios.di.provider.ScenariosApiProvider
import ru.dekabrsky.scenarios.domain.interactor.ScenariosInteractorImpl
import ru.dekabrsky.scenarios_common.domain.interactor.ScenariosInteractor
import toothpick.config.Module

class ScenariosFeatureModule: Module() {
    init {
        bind(ScenariosApi::class.java).toProvider(ScenariosApiProvider::class.java)
        bind(ScenariosInteractor::class.java) to ScenariosInteractorImpl::class.java
    }
}