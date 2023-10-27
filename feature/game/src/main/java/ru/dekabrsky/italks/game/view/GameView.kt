package ru.dekabrsky.italks.game.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter

@AddToEndSingle
interface GameView : MvpView {
    fun setupAvatar(router: FlowRouter)
}