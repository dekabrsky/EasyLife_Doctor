package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.easylife.basic.resources.ResourceProvider
import ru.dekabrsky.feature.notifications.common.presentation.model.NotificationsFlowArgs
import ru.dekabrsky.feature.notifications.common.utils.NotificationToStringFormatter
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.DoctorNotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.view.DoctorNotificationsListView
import javax.inject.Inject

class DoctorNotificationListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: DoctorNotificationInteractor,
    private val flowArgs: NotificationsFlowArgs,
    private val formatter: NotificationToStringFormatter,
    private val resourceProvider: ResourceProvider,
    private val errorHandler: ServerErrorHandler
): BaseNotificationListPresenter<DoctorNotificationsListView>(
    router,
    interactor,
    flowArgs,
    formatter,
    errorHandler
) {
    init {
        flowArgs.patientId?.let { interactor.setUserId(it) }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setTitle(resourceProvider.getString(R.string.notifications) + " " + flowArgs.patientName.orEmpty())
    }
}