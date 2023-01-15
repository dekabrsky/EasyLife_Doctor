package ru.dekabrsky.dialings.presentation.presenter

import ru.dekabrsky.dialings.presentation.view.DialingsFlowView
import ru.dekabrsky.common.presentation.model.EventsFlowScreenArgs
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import javax.inject.Inject

class DialingsFlowPresenter @Inject constructor(
    private val router: FlowRouter,
    private val args: EventsFlowScreenArgs
): BasicPresenter<DialingsFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(args.screenKey, args.dialingId)
    }
}