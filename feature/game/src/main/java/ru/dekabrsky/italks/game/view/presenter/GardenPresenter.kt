package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.view.GardenView
import ru.dekabrsky.simple_bottomsheet.view.model.BottomSheetScreenArgs
import javax.inject.Inject

class GardenPresenter @Inject constructor(
    val router: FlowRouter
) : BasicPresenter<GardenView>(router) {
    fun goToHouse() {
        router.backTo(Flows.Game.SCREEN_MAIN_ROOM)
    }

    fun startFlappyBird() {
        viewState.startFlappyBirdActivity()
    }

    fun goToFootball() {
        router.navigateTo(Flows.Common.SCREEN_BOTTOM_INFO, BottomSheetScreenArgs(title = "Какой-то всратый заголовок", subtitle = "Не менее всратый текст"))
        //viewState.startFootballActivity()
    }

    fun goToLeaves() {
        viewState.startLeavesActivity()
    }

    fun goToPyat() {
        viewState.startPyatActivity()
    }
}