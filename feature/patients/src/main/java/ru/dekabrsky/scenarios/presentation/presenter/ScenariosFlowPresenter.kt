package ru.dekabrsky.scenarios.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.scenarios.presentation.view.PatientsFlowView
import javax.inject.Inject

class ScenariosFlowPresenter @Inject constructor(
    private val router: FlowRouter
): BasicPresenter<PatientsFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Patients.SCREEN_PATIENTS_LIST)
    }
}