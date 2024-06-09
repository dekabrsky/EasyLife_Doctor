package ru.dekabrsky.login.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.model.ReLoginType
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.login.presentation.view.LoginFlowView
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import javax.inject.Inject

class LoginFlowPresenter @Inject constructor(
    private val router: FlowRouter,
    private val preferencesProvider: SharedPreferencesProvider,
    private val reLoginType: ReLoginType
) : BasicPresenter<LoginFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val rootScreen = when {
            reLoginType == ReLoginType.PASSWORD ->
                Flows.Login.SCREEN_LOGIN

            reLoginType == ReLoginType.PIN && preferencesProvider.hasPin.get() ->
                Flows.Login.SCREEN_PIN_LOGIN

            preferencesProvider.hasPin.get() ->
                Flows.Login.SCREEN_PIN_LOGIN

            else ->
                Flows.Login.SCREEN_LOGIN
        }
        router.newRootScreen(rootScreen)
    }
}

