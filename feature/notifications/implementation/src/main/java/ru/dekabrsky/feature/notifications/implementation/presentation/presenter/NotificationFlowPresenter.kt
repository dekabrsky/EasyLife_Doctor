package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationFlowView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import javax.inject.Inject

class NotificationFlowPresenter @Inject constructor(
    private val router: FlowRouter,
) : BasicPresenter<NotificationFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Notifications.SCREEN_NOTIFICATIONS_LIST)
    }
}