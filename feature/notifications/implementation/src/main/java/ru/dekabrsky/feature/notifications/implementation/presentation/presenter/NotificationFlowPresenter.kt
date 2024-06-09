package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.feature.notifications.common.presentation.model.NotificationsFlowArgs
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationFlowView
import javax.inject.Inject

class NotificationFlowPresenter @Inject constructor(
    private val router: FlowRouter,
    private val args: NotificationsFlowArgs
) : BasicPresenter<NotificationFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (args.patientId != null) {
            router.newRootScreen(Flows.Notifications.SCREEN_DOCTOR_NOTIFICATIONS_LIST)
        } else {
            router.newRootScreen(Flows.Notifications.SCREEN_CHILD_NOTIFICATIONS_LIST)
        }

    }
}