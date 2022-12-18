package ru.dekabrsky.italks.game.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface GameView : MvpView {
    fun startGameActivity()
}