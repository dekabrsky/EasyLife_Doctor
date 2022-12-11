package ru.dekabrsky.callersbase.presentation.presenter

import ru.dekabrsky.callersbase_common.presentation.model.CallersBasesFlowScreenArgs
import ru.dekabrsky.callersbase.presentation.view.CallersBaseFlowView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import javax.inject.Inject

class CallersBaseFlowPresenter @Inject constructor(
    private val router: FlowRouter,
    private val args: CallersBasesFlowScreenArgs
): BasicPresenter<CallersBaseFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(args.screenKey, args.callersBaseEntity)
    }
}