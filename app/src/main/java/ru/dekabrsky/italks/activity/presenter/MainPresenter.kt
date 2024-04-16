package ru.dekabrsky.italks.activity.presenter

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.dekabrsky.feature.loginCommon.presentation.model.TokenCache
import ru.dekabrsky.italks.activity.view.MainView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.login.domain.interactor.LoginInteractorImpl
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val router: FlowRouter,
    private val loginInteractorImpl: LoginInteractorImpl,
    private val tokenCache: TokenCache
): BasicPresenter<MainView>() {

    private var expiresDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        startFlow(Flows.Login.name)

        tokenCache.accessTokenReceived
            .subscribeOnIo()
            .subscribe({ startExpireTimer() }, {})
            .addFullLifeCycle()
    }

    @Suppress("MagicNumber")
    private fun startExpireTimer() {
        val expiresIn = tokenCache.expiresIn ?: return
        val refreshToken = tokenCache.refreshToken ?: return

        expiresDisposable?.dispose()
        expiresDisposable = Single.timer(expiresIn * 9L / 10, TimeUnit.SECONDS)
            .flatMap { loginInteractorImpl.refresh(refreshToken) }
            .subscribeOnIo()
            .subscribe({}, {})
            .addFullLifeCycle()
    }

    private fun startFlow(flowName: String, data: Any? = null) {
        router.preSetScreens(Pair(flowName, data))
        router.toggleScreen(flowName, "")
    }

    fun onAttach() = onFirstViewAttach()

    fun toTesterSettings() {
        router.startFlow(Flows.TesterSettings.name)
    }
}
