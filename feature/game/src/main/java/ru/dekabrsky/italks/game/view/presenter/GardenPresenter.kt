package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.view.GardenView
import javax.inject.Inject

class GardenPresenter @Inject constructor(
    val router: FlowRouter
) : BasicPresenter<GardenView>(router) {
    fun goToHouse() {
        router.backTo(Flows.Game.SCREEN_MAIN_ROOM)
    }

    fun startFlappyBird() {
        viewState.startGameActivity()
    }
}