package ru.dekabrsky.callersbase.di.module

import ru.dekabrsky.callersbase.data.api.ContactsApi
import ru.dekabrsky.callersbase.di.provider.CallersBaseApiProvider
import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.callersbase_common.domain.interactor.ContactsInteractor
import toothpick.config.Module

class CallersBasesFeatureModule: Module() {
    init {
        bind(ContactsApi::class.java).toProvider(CallersBaseApiProvider::class.java)
        bind(ContactsInteractor::class.java).to(ContactsInteractorImpl::class.java)
    }
}