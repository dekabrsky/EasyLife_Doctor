package ru.dekabrsky.callersbase.presentation.presenter

import ru.dekabrsky.callersbase.presentation.view.ChatFlowView
import ru.dekabrsky.common.presentation.model.ChatsFlowScreenArgs
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import javax.inject.Inject

class ChatFlowPresenter @Inject constructor(
    private val router: FlowRouter,
    private val args: ChatsFlowScreenArgs
): BasicPresenter<ChatFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(args.screenKey, args.callersBaseEntity)
    }
}