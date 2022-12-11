package ru.dekabrsky.dialings.presentation.presenter

import ru.dekabrsky.dialings.presentation.view.DialingsFlowView
import ru.dekabrsky.dialings_common.presentation.model.DialingsFlowScreenArgs
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import javax.inject.Inject

class DialingsFlowPresenter @Inject constructor(
    private val router: FlowRouter,
    private val args: DialingsFlowScreenArgs
): BasicPresenter<DialingsFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(args.screenKey, args.dialingId)
    }
}