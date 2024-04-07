package ru.dekabrsky.stats.presentation.presenter

import ru.dekabrsky.common.domain.interactor.ContactsInteractor
import ru.dekabrsky.feature.loginCommon.domain.interactor.LoginInteractor
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.stats.presentation.view.AdultProfileView
import javax.inject.Inject

class AdultProfilePresenter @Inject constructor(
    private val router: FlowRouter,
    private val loginInteractor: LoginInteractor,
    private val loginDataCache: LoginDataCache,
    private val contactsInteractor: ContactsInteractor
): BasicPresenter<AdultProfileView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showMyInfo(loginDataCache.currentUserData)
        if (loginDataCache.currentUserData?.role != UserType.PARENT) return
        contactsInteractor.getChildren()
            .subscribeOnIo()
            .subscribe(viewState::showChildInfo, viewState::showError)
            .addFullLifeCycle()
    }

    fun onLogoutClicked() {
        viewState.showLogoutDialog()
    }

    fun onLogoutConfirmed() {
        loginInteractor.getFcmToken()
            .flatMapCompletable { token -> loginInteractor.logout(token) }
            .subscribeOnIo()
            .withLoadingView(viewState)
            .subscribe({ router.newRootFlow(Flows.Login.name) }, viewState::showError)
            .addFullLifeCycle()
    }

}