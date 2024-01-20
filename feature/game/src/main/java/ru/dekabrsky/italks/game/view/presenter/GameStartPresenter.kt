package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.view.GameView
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class GameStartPresenter @Inject constructor(
    val router: FlowRouter,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val notification: NotificationEntity,
    private val cache: GameFlowCache
) : BasicPresenter<GameView>(router) {

    override fun onFirstViewAttach() {
        viewState.setupAvatar(router)

        if (!notification.tabletName.isNullOrEmpty()) {
            openBottomSheet(notification)
        }
    }

    private fun openBottomSheet(notification: NotificationEntity) {
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Меня просили напомнить",
                subtitle = notification.tabletName + "\n" + notification.dosage + "\n" + notification.note,
                mode = BottomSheetMode.GAME,
                buttonState = ButtonState(
                    text = "Так точно, капитан!",
                    action = {
                        cache.isFromNotification = true
                        onGameStartClicked()
                    }
                ),
                icon = R.drawable.pills
            )
        )
    }

    fun onGameStartClicked() {
        if (sharedPreferencesProvider.gameAvatar.get().isEmpty()) {
            router.startFlow(Flows.Avatar.name)
        } else {
            router.navigateTo(Flows.Game.SCREEN_MAIN_ROOM)
        }
    }
}