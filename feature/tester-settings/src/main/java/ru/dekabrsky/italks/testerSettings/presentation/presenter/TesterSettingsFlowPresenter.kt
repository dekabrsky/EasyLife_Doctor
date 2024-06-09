package ru.dekabrsky.easylife.testerSettings.presentation.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.testerSettings.presentation.view.TesterSettingsFlowView
import javax.inject.Inject

class TesterSettingsFlowPresenter @Inject constructor(
    private val router: FlowRouter
): BasicPresenter<TesterSettingsFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.TesterSettings.SCREEN_TESTER_SETTINGS)
    }
}