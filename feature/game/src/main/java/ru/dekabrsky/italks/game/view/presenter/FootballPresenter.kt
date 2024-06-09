package ru.dekabrsky.easylife.game.view.presenter

import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.analytics.AnalyticsUtils
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.basic.rx.withLoadingView
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.game.data.domain.interactor.GameInteractor
import ru.dekabrsky.easylife.game.data.domain.model.GameType
import ru.dekabrsky.easylife.game.view.FootballView
import ru.dekabrsky.easylife.game.view.cache.GameFlowCache
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class FootballPresenter @Inject constructor(
    val router: FlowRouter,
    private val interactor: GameInteractor,
    private val cache: GameFlowCache,
    private val analyticsSender: AnalyticsSender,
    private val errorHandler: ServerErrorHandler
) : BasicPresenter<FootballView>(router) {

    override fun onBackPressed() = exitWithConfirm { router.back() }

    fun saveProgress(score: Int, usePillMultiplier: Boolean){
        val footballInfo = cache.configs.find { it.type == GameType.TicTacToe } ?: return
        val id = footballInfo.gameId
        interactor.postGameProgress(id, score, usePillMultiplier)
            .subscribeOnIo()
            .withLoadingView(viewState)
            .subscribe({ cache.experience = it }, { errorHandler.onError(it, viewState) })
            .addFullLifeCycle()
        AnalyticsUtils.sendScreenOpen(this, analyticsSender)
    }

    fun restart() {
        router.replaceScreen(Flows.Game.SCREEN_FOOTBALL)
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
                buttonState = ButtonState("Да, выйти") { exitAction.invoke() }
            )
        )
    }
}