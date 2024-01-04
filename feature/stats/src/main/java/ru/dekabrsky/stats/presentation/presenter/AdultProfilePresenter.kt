package ru.dekabrsky.stats.presentation.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.dekabrsky.common.domain.interactor.ContactsInteractor
import ru.dekabrsky.feature.loginCommon.domain.interactor.LoginInteractor
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.stats.domain.interactor.StatsInteractor
import ru.dekabrsky.stats.presentation.mapper.StatsEntityToUiMapper
import ru.dekabrsky.stats.presentation.view.AdultProfileView
import javax.inject.Inject
import kotlin.math.log

class AdultProfilePresenter @Inject constructor(
    private val router: FlowRouter,
    private val loginInteractor: LoginInteractor,
    private val loginDataCache: LoginDataCache,
    private val contactsInteractor: ContactsInteractor
): BasicPresenter<AdultProfileView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showMyInfo(loginDataCache.currentUserData)
        contactsInteractor.getChildren()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(viewState::showChildInfo, viewState::showError)
            .addFullLifeCycle()
    }

    fun onLogoutClick() {
        loginInteractor.logout()
            .observeOn(AndroidSchedulers.mainThread())
            .withLoadingView(viewState)
            .subscribe({ router.newRootFlow(Flows.Login.name) }, viewState::showError)
            .addFullLifeCycle()

    }

}