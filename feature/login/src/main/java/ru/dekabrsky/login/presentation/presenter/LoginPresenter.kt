package ru.dekabrsky.login.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.tabs.domain.UserType
import ru.dekabrsky.italks.tabs.presentation.model.TabsFlowArgs
import ru.dekabrsky.login.data.repository.LoginRepository
import ru.dekabrsky.login.presentation.view.LoginView
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val router: FlowRouter,
    private val repository: LoginRepository
): BasicPresenter<LoginView>() {

    var currentLogin: String = ""
    var currentPassword: String = ""

    fun onLoginClick() {
        repository.login(currentLogin, currentPassword)
            .observeOn(RxSchedulers.main())
            .subscribe({
                router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
            }, viewState::showError)
            .addFullLifeCycle()

    }

    fun onLoginChanged(text: CharSequence) {
        currentLogin = text.toString()
    }

    fun onPasswordChanged(text: CharSequence) {
        currentPassword = text.toString()
    }
}