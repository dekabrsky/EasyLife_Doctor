package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.view.FifteenView
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class FifteenPresenter @Inject constructor(
    private val router: FlowRouter
): BasicPresenter<FifteenView>(router) {
    override fun onBackPressed() = exitWithConfirm { router.back() }

    fun exitGame() {
        exitWithConfirm { router.backTo(Flows.Game.SCREEN_START_GAME) }
    }

    fun goToHome() {
        exitWithConfirm { router.backTo(Flows.Game.SCREEN_MAIN_ROOM) }
    }

    fun goToGarden() {
        exitWithConfirm { router.backTo(Flows.Game.SCREEN_GARDEN) }
    }

    private fun exitWithConfirm(exitAction: () -> Unit) {
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Выйти из игры?",
                subtitle = "Твой прогресс не сохранится",
                mode = BottomSheetMode.GAME,
                icon = R.drawable.barbecue,
                buttonState = ButtonState("Да, выйти") { exitAction.invoke() }
            )
        )
    }
}