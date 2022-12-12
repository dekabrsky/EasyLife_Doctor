package ru.dekabrsky.scenarios.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.scenarios.presentation.view.ScenariosFlowView
import javax.inject.Inject

class ScenariosFlowPresenter @Inject constructor(
    private val router: FlowRouter
): BasicPresenter<ScenariosFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Patients.SCREEN_SCENARIOS_LIST)
    }
}