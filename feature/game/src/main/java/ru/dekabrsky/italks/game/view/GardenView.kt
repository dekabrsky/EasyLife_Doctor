package ru.dekabrsky.italks.game.view

import android.content.Context
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.game.FlappyBird

@AddToEndSingle
interface GardenView: BasicView {
    @OneExecution
    fun startFlappyBirdActivity()
    @OneExecution
    fun startLeavesActivity()
    fun setupAvatar(router: FlowRouter)
}