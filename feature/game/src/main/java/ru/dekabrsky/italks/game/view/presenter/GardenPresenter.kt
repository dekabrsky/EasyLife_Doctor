package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.analytics.AnalyticsUtils
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.data.domain.interactor.GameInteractor
import ru.dekabrsky.italks.game.data.domain.model.GameConfigEntity
import ru.dekabrsky.italks.game.data.domain.model.GameType
import ru.dekabrsky.italks.game.view.GardenView
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class GardenPresenter @Inject constructor(
    val router: FlowRouter,
    private val interactor: GameInteractor,
    private val cache: GameFlowCache,
    private val analyticsSender: AnalyticsSender
) : BasicPresenter<GardenView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setupAvatar(router)
        interactor.getGamesConfigs()
            .subscribeOnIo()
            .withLoadingView(viewState)
            .subscribe(::dispatchConfigs, viewState::showError)
            .addFullLifeCycle()
        AnalyticsUtils.sendScreenOpen(this, analyticsSender)
    }

    private fun dispatchConfigs(config: List<GameConfigEntity>) {
        cache.configs = config
        if (cache.isFromNotification) {
            when (cache.configs.map { it.type }.random()) {
                GameType.Flappy -> startFlappyBird()
                GameType.Barbecue -> goToFifteen()
                GameType.Leaves -> goToLeaves()
                GameType.TicTacToe -> goToFootball()
                else -> {}
            }

            cache.isFromNotification = false
        }

    }

    fun goToHouse() {
        router.backTo(Flows.Game.SCREEN_MAIN_ROOM)
    }

    fun fullScore(): String {
        return cache.experience?.score.toString()
    }

    fun startFlappyBird() {
        val flappyInfo = cache.configs.find { it.type == GameType.Flappy } ?: return
        if (!flappyInfo.enabled) {
            viewState.showToast("Сейчас мы улучшаем игру, она скоро снова будет доступна!")
        } else {
            router.navigateTo(
                Flows.Common.SCREEN_BOTTOM_INFO,
                BottomSheetScreenArgs(
                    title = flappyInfo.displayName,
                    subtitle = flappyInfo.description,
                    mode = BottomSheetMode.GAME,
                    icon = R.drawable.helicopter_preview,
                    buttonState = ButtonState("Играть") {
                        viewState.startFlappyBirdActivity(
                            flappyInfo.gameId
                        )
                    }
                )
            )
        }
    }

    fun goToFootball() {
        val footballInfo = cache.configs.find { it.type == GameType.TicTacToe } ?: return
        if (!footballInfo.enabled) {
            viewState.showToast("Сейчас мы улучшаем игру, она скоро снова будет доступна!")
        } else {
            router.navigateTo(
                Flows.Common.SCREEN_BOTTOM_INFO,
                BottomSheetScreenArgs(
                    title = footballInfo.displayName,
                    subtitle = footballInfo.description,
                    mode = BottomSheetMode.GAME,
                    icon = R.drawable.avatar_cat,
                    buttonState = ButtonState("Отлично") { router.navigateTo(Flows.Game.SCREEN_FOOTBALL) }
                )
            )
        }
    }

    fun goToLeaves() {
        val leavesInfo = cache.configs.find { it.type == GameType.Leaves } ?: return
        if (!leavesInfo.enabled) {
            viewState.showToast("Сейчас мы улучшаем игру, она скоро снова будет доступна!")
        } else {
            router.navigateTo(
                Flows.Common.SCREEN_BOTTOM_INFO,
                BottomSheetScreenArgs(
                    title = leavesInfo.displayName,
                    subtitle = leavesInfo.description,
                    mode = BottomSheetMode.GAME,
                    icon = R.drawable.leaf,
                    buttonState = ButtonState("Отлично") { router.navigateTo(Flows.Game.SCREEN_LEAVES) }
                ))
        }
    }

    fun goToFifteen() {
        val barbecueInfo = cache.configs.find { it.type == GameType.Barbecue } ?: return
        if (!barbecueInfo.enabled) {
            viewState.showToast("Сейчас мы улучшаем игру, она скоро снова будет доступна!")
        } else {
            router.navigateTo(
                Flows.Common.SCREEN_BOTTOM_INFO,
                BottomSheetScreenArgs(
                    title = barbecueInfo.displayName,
                    subtitle = barbecueInfo.description,
                    mode = BottomSheetMode.GAME,
                    icon = R.drawable.hedgehog,
                    buttonState = ButtonState("Отлично") { router.navigateTo(Flows.Game.SCREEN_FIFTEEN) }
                )
            )
        }
    }
}