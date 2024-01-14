package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.data.domain.interactor.GameInteractor
import ru.dekabrsky.italks.game.data.domain.model.GameType
import ru.dekabrsky.italks.game.view.FifteenView
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class FifteenPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: GameInteractor,
    private val cache: GameFlowCache
): BasicPresenter<FifteenView>(router) {
    override fun onBackPressed() = exitWithConfirm { router.back() }

    fun saveProgress(score: Int, usePillMultiplier: Boolean){
        val fifteenInfo = cache.configs.find { it.type == GameType.Barbecue } ?: return
        val id = fifteenInfo.gameId
        interactor.postGameProgress(id, score, usePillMultiplier)
            .observeOn(RxSchedulers.main())
            .withLoadingView(viewState)
            .subscribe({ cache.experience = it }, viewState::showError)
            .addFullLifeCycle()
    }

    fun restart() {
        router.replaceScreen(Flows.Game.SCREEN_FIFTEEN)
    }

    fun exitGame() {
        exitWithConfirm { router.backTo(Flows.Game.SCREEN_GARDEN) }
    }

    private fun exitWithConfirm(exitAction: () -> Unit) {
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Выйти из игры?",
                subtitle = "Твой прогресс не сохранится, если ты выйдешь в процессе игры",
                mode = BottomSheetMode.GAME,
                icon = R.drawable.hedgehog,
                buttonState = ButtonState("Да, выйти") { exitAction.invoke() }
            )
        )
    }
}