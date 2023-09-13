package ru.dekabrsky.login.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.tabs.presentation.model.TabsFlowArgs
import ru.dekabrsky.login.data.repository.LoginRepository
import ru.dekabrsky.login.presentation.view.LoginView
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val router: FlowRouter,
    private val repository: LoginRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider
): BasicPresenter<LoginView>() {

    enum class LoginMode { LOGIN, REGISTRATION }

    var currentCode: String = ""
    var currentLogin: String = ""
    var currentPassword: String = ""
    var lastLogin = sharedPreferencesProvider.lastLogin.get()

    private var mode: LoginMode = LoginMode.LOGIN

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setLogin(lastLogin)
//        repository.login("Denis", "123")
//            .observeOn(RxSchedulers.main())
//            .subscribe({
//                router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
//            }, viewState::showError)
//            .addFullLifeCycle()
    }

    fun onDoneButtonClick() {
        when (mode) {
            LoginMode.LOGIN -> {
                repository.login(currentLogin, currentPassword)
                    .observeOn(RxSchedulers.main())
                    .subscribe({
                        router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
                    }, viewState::showError)
                    .addFullLifeCycle()
                if (lastLogin != currentLogin) sharedPreferencesProvider.lastLogin.set(currentLogin)
            }
            LoginMode.REGISTRATION -> {
                repository.registration(currentCode, currentLogin, currentPassword)
                    .observeOn(RxSchedulers.main())
                    .subscribe({
                        router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
                    }, viewState::showError)
                    .addFullLifeCycle()
            }
        }
    }

    fun onChangeModeClick() {
        when (mode) {
            LoginMode.LOGIN -> {
                mode = LoginMode.REGISTRATION
                viewState.setupForRegistration()
            }
            LoginMode.REGISTRATION -> {
                mode = LoginMode.LOGIN
                viewState.setupForLogin()
            }
        }
    }

    fun onLoginChanged(text: CharSequence) {
        currentLogin = text.toString()
    }

    fun onPasswordChanged(text: CharSequence) {
        currentPassword = text.toString()
    }

    fun onCodeTextChanged(text: String) {
        currentCode = text
    }
}