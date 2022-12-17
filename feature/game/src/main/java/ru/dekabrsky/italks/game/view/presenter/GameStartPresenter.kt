package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.game.view.GameView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import javax.inject.Inject

class GameStartPresenter @Inject constructor(
    router: FlowRouter
) : BasicPresenter<GameView>(router) {


    fun onGameStartClicked() {
        viewState.startGameActivity()
    }
}