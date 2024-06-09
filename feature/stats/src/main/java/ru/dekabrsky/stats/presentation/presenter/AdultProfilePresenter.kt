package ru.dekabrsky.stats.presentation.presenter

import ru.dekabrsky.common.domain.interactor.ContactsInteractor
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.basic.rx.withLoadingView
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.feature.loginCommon.domain.interactor.LoginInteractor
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.stats.presentation.view.AdultProfileView
import ru.dekabrsky.webview.presentation.model.WebViewArgs
import javax.inject.Inject

class AdultProfilePresenter @Inject constructor(
    private val router: FlowRouter,
    private val loginInteractor: LoginInteractor,
    private val loginDataCache: LoginDataCache,
    private val contactsInteractor: ContactsInteractor,
    private val errorHandler: ServerErrorHandler
): BasicPresenter<AdultProfileView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showMyInfo(loginDataCache.currentUserData)
        if (loginDataCache.currentUserData?.role != UserType.PARENT) return
        contactsInteractor.getChildren()
            .subscribeOnIo()
            .subscribe(viewState::showChildInfo, { errorHandler.onError(it, viewState) })
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
            .subscribe({ router.newRootFlow(Flows.Login.name) }, { errorHandler.onError(it, viewState) })
            .addFullLifeCycle()
    }

    fun onTermsTextClick() {
        router.navigateTo(
            Flows.Common.SCREEN_WEB_VIEW,
            WebViewArgs(Scopes.SCOPE_FLOW_LOGIN, TERMS_URL)
        )
    }

    fun onPolicyTextClick() {
        router.navigateTo(
            Flows.Common.SCREEN_WEB_VIEW,
            WebViewArgs(Scopes.SCOPE_FLOW_LOGIN, POLICY_URL)
        )
    }

    companion object {
        private const val TERMS_URL = "https://easylife-project.ru/terms_of_use"
        private const val POLICY_URL = "https://easylife-project.ru/privacy_policy"
    }

}