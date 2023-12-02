package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.view.FootballView
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class FootballPresenter @Inject constructor(
    val router: FlowRouter,
    private val cache: GameFlowCache
) : BasicPresenter<FootballView>(router) {

    override fun onBackPressed() = exitWithConfirm { router.back() }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showToast(cache.configs.joinToString { it.displayName })
    }

    fun restart() {
        router.replaceScreen(Flows.Game.SCREEN_FOOTBALL)
    }

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
                subtitle = "Твой прогресс не сохранится, если ты выйдешь в процессе игры",
                mode = BottomSheetMode.GAME,
                buttonState = ButtonState("Да, выйти") { exitAction.invoke() }
            )
        )
    }
}