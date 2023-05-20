package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.view.GardenView
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class GardenPresenter @Inject constructor(
    val router: FlowRouter
) : BasicPresenter<GardenView>(router) {
    fun goToHouse() {
        router.backTo(Flows.Game.SCREEN_MAIN_ROOM)
    }

    fun startFlappyBird() {
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Время понаблюдать за птицами",
                subtitle = "Помоги птичке пройти через все преграды",
                mode = BottomSheetMode.GAME,
                icon = R.drawable.bird,
                buttonState = ButtonState("Отлично", viewState::startFlappyBirdActivity)
            )
        )
    }

    fun goToFootball() {
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Молодец!",
                subtitle = "Пойдем сыграем в мяч?",
                mode = BottomSheetMode.GAME,
                icon = R.drawable.avatar_cat,
                buttonState = ButtonState("Отлично") { router.navigateTo(Flows.Game.SCREEN_FOOTBALL) }
            )
        )
    }

    fun goToLeaves() {
        viewState.startLeavesActivity()
    }

    fun goToFifteen() {
        router.navigateTo(Flows.Game.SCREEN_FIFTEEN)
    }
}