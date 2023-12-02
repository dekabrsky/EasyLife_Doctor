package ru.dekabrsky.italks.game.view.presenter

import android.os.Build
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.domain.interactor.GameInteractor
import ru.dekabrsky.italks.game.domain.model.GameType
import ru.dekabrsky.italks.game.view.GardenView
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class GardenPresenter @Inject constructor(
    val router: FlowRouter,
    private val interactor: GameInteractor,
    private val cache: GameFlowCache
) : BasicPresenter<GardenView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setupAvatar(router)
        interactor.getGamesConfigs()
            .observeOn(RxSchedulers.main())
            .doOnSubscribe { viewState.setLoadingVisibility(true) }
            .doFinally { viewState.setLoadingVisibility(false) }
            .subscribe({ cache.configs = it }, viewState::showError)
            .addFullLifeCycle()
    }

    fun goToHouse() {
        router.backTo(Flows.Game.SCREEN_MAIN_ROOM)
    }

    fun startFlappyBird() {
        val flappyInfo = cache.configs.find { it.type == GameType.Flappy } ?: return
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = flappyInfo.displayName,
                subtitle = flappyInfo.description,
                mode = BottomSheetMode.GAME,
                icon = R.drawable.bird,
                buttonState = ButtonState("Играть", viewState::startFlappyBirdActivity)
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
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "А какая погода?",
                subtitle = "Время пособирать листики!",
                mode = BottomSheetMode.GAME,
                icon = R.drawable.leaf,
                buttonState = ButtonState("Отлично") { router.navigateTo(Flows.Game.SCREEN_LEAVES) }
        ))
    }

    fun goToFifteen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            router.navigateTo(Flows.Game.SCREEN_FIFTEEN)
        } else {
            router.navigateTo(
                Flows.Common.SCREEN_BOTTOM_INFO,
                BottomSheetScreenArgs(
                    title = "Ошибка",
                    subtitle = "Эта игра не поддерживается на твоем устройстве",
                    mode = BottomSheetMode.GAME
                )
            )
        }
    }
}