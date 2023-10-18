package ru.dekabrsky.italks.game.view

import android.content.Context
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.italks.game.FlappyBird

interface GardenView: BasicView {
    @OneExecution
    fun startFlappyBirdActivity()
    @OneExecution
    fun startLeavesActivity()
}