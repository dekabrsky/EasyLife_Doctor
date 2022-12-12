package ru.dekabrsky.dialings.di

import ru.dekabrsky.dialings.data.api.DialingsApi
import ru.dekabrsky.dialings.domain.interactor.DialingsInteractorImpl
import ru.dekabrsky.dialings_common.domain.interactor.DialingsInteractor
import toothpick.config.Module

class DialingsFeatureModule: Module() {
    init {
        bind(DialingsApi::class.java).toProvider(DialingsApiProvider::class.java)
        bind(DialingsInteractor::class.java).to(DialingsInteractorImpl::class.java)
    }
}