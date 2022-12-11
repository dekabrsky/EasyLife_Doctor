package ru.dekabrsky.italks.testerSettings.presentation.presenter

import android.content.Context
import ru.dekabrsky.italks.basic.di.ServerEndpoint
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.testerSettings.presentation.view.TesterSettingsView
import javax.inject.Inject

class TesterSettingsPresenter @Inject constructor(
    private val router: FlowRouter,
    @ServerEndpoint val baseEndpoint: String,
    private val context: Context
): BasicPresenter<TesterSettingsView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setupServerAddress(baseEndpoint)
    }

    fun onSaveBtnClicked(newAddress: String) {
        context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
            .edit()
            .putString("TEST_URL", newAddress)
            .apply()

        restartApp()
    }

    private fun restartApp() {
        router.finishChain()
        router.navigateToStart()
        viewState.restartApp()
    }
}