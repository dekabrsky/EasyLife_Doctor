package ru.dekabrsky.login.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.login.presentation.view.LoginFlowView
import javax.inject.Inject

class LoginFlowPresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<LoginFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Login.SCREEN_LOGIN)
    }
}