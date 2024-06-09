package ru.dekabrsky.easylife.game.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.easylife.basic.fragments.BasicView
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter

@AddToEndSingle
interface GardenView: BasicView {
    @OneExecution
    fun startFlappyBirdActivity(gameId: Int)
    @OneExecution
    fun startLeavesActivity()
    fun setupAvatar(router: FlowRouter)
}