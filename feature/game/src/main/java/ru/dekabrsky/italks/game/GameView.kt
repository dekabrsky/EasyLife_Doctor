package main.java.ru.dekabrsky.italks.game

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface GameView : MvpView {
    fun setActivity()
}