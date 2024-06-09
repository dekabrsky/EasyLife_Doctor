package ru.dekabrsky.stats.presentation.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.stats.presentation.view.AdultProfileFlowView
import javax.inject.Inject

class AdultProfileFlowPresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<AdultProfileFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Stats.SCREEN_MAIN_STATS)
    }
}