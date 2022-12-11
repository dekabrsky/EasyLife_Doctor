package ru.dekabrsky.italks.testerSettings.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.testerSettings.presentation.view.TesterSettingsFlowView
import javax.inject.Inject

class TesterSettingsFlowPresenter@Inject constructor(
    private val router: FlowRouter
): BasicPresenter<TesterSettingsFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.TesterSettings.SCREEN_TESTER_SETTINGS)
    }
}