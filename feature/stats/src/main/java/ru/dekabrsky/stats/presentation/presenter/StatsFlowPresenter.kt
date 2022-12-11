package ru.dekabrsky.stats.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.stats.presentation.view.StatsFlowView
import javax.inject.Inject

class StatsFlowPresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<StatsFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Stats.SCREEN_MAIN_STATS)
    }
}