package ru.dekabrsky.italks.game

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import javax.inject.Inject

class GameFlowPresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<GameFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Game.SCREEN_GAME)
    }
}

