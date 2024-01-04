package ru.dekabrsky.stats.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
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