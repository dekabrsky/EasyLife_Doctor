package ru.dekabrsky.easylife.game.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter

@AddToEndSingle
interface GameView : MvpView {
    fun setupAvatar(router: FlowRouter)
}