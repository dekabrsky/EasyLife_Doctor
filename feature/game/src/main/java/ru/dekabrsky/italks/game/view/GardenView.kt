package ru.dekabrsky.italks.game.view

import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.italks.basic.fragments.BasicView

interface GardenView: BasicView {
    @OneExecution
    fun startFlappyBirdActivity()
    @OneExecution
    fun startLeavesActivity()
}