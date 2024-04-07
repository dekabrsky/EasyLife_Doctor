package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.analytics.AnalyticsUtils
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.data.domain.interactor.GameInteractor
import ru.dekabrsky.italks.game.data.domain.model.GameType
import ru.dekabrsky.italks.game.view.LeavesView
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class LeavesPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: GameInteractor,
    private val cache: GameFlowCache,
    private val analyticsSender: AnalyticsSender
): BasicPresenter<LeavesView>(router) {

    override fun onBackPressed() = exitWithConfirm { router.back() }

    fun saveProgress(score: Int, usePillMultiplier: Boolean){
        val leavesInfo = cache.configs.find { it.type == GameType.Leaves } ?: return
        val id = leavesInfo.gameId
        interactor.postGameProgress(id, score, usePillMultiplier)
            .subscribeOnIo()
            .withLoadingView(viewState)
            .subscribe({ cache.experience = it }, viewState::showError)
            .addFullLifeCycle()
        AnalyticsUtils.sendScreenOpen(this, analyticsSender)
    }

    fun restart() {
        router.replaceScreen(Flows.Game.SCREEN_LEAVES)
    }

    fun exitGame() {
        exitWithConfirm { router.backTo(Flows.Game.SCREEN_GARDEN) }
    }

    private fun exitWithConfirm(exitAction: () -> Unit) {
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Выйти из игры?",
                subtitle = "Ты можешь заработать больше коинов, если продолжишь играть!",
                mode = BottomSheetMode.GAME,
                icon = R.drawable.barbecue,
                buttonState = ButtonState("Да, выйти") { exitAction.invoke() }
            )
        )
    }
}