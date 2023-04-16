package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.game.view.GameView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import javax.inject.Inject

class GameStartPresenter @Inject constructor(
    val router: FlowRouter
) : BasicPresenter<GameView>(router) {


    fun onGameStartClicked() {
        //viewState.startGameActivity()
        router.navigateTo(Flows.Game.SCREEN_MAIN_ROOM)
    }
}