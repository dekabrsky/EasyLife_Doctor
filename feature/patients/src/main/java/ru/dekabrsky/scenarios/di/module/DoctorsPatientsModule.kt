package ru.dekabrsky.scenarios.di.module

import ru.dekabrsky.scenarios.data.api.DoctorPatientsApi
import ru.dekabrsky.scenarios.di.provider.DoctorsPatientsApiProvider
import ru.dekabrsky.scenarios.domain.interactor.DoctorPatientsInteractorImpl
import ru.dekabrsky.common.domain.interactor.DoctorPatientsInteractor
import toothpick.config.Module

class DoctorsPatientsModule: Module() {
    init {
        bind(DoctorPatientsApi::class.java).toProvider(DoctorsPatientsApiProvider::class.java)
        bind(DoctorPatientsInteractor::class.java) to DoctorPatientsInteractorImpl::class.java
    }
}