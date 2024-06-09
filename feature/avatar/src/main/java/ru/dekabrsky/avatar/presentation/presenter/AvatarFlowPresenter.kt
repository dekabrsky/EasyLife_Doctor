package ru.dekabrsky.avatar.presentation.presenter

import ru.dekabrsky.avatar.presentation.view.AvatarFlowView
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import javax.inject.Inject

class AvatarFlowPresenter  @Inject constructor(
    private val router: FlowRouter
): BasicPresenter<AvatarFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Avatar.SCREEN_AVATAR_SELECTION)
    }
}