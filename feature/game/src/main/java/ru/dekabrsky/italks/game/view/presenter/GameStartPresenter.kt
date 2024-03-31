package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.italks.game.view.GameView
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationMedicineEntity
import ru.dekabrsky.feature.notifications.common.utils.NotificationToStringFormatter
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.resources.ResourceProvider
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

@Suppress("LongParameterList")
class GameStartPresenter @Inject constructor(
    val router: FlowRouter,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val analyticsSender: AnalyticsSender,
    private val notification: NotificationEntity,
    private val cache: GameFlowCache,
    private val notificationToStringFormatter: NotificationToStringFormatter
) : BasicPresenter<GameView>(router) {

    override fun onFirstViewAttach() {
        viewState.setupAvatar(router)

        if (notification.medicines.isNotEmpty()) {
            openBottomSheet(notification)
        }
    }

    private fun openBottomSheet(notification: NotificationEntity) {
        analyticsSender.sendEvent("notification_received")
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Меня просили напомнить",
                subtitle = notificationToStringFormatter.formatMedicines(notification),
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
        if (cache.isFromNotification) {
            analyticsSender.sendEvent("game_from_notification")
        }

        if (sharedPreferencesProvider.gameAvatar.get().isEmpty()) {
            router.startFlow(Flows.Avatar.name)
        } else {
            router.navigateTo(Flows.Game.SCREEN_MAIN_ROOM)
        }
    }
}