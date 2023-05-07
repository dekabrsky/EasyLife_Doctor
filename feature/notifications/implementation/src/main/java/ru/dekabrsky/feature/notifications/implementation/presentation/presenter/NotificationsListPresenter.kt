package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.NotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationsListView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import javax.inject.Inject

class NotificationsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val notificationInteractor: NotificationInteractor
): BasicPresenter<NotificationsListView>(router) {

    override fun attachView(view: NotificationsListView) {
        super.attachView(view)
        getAll()
    }

    private fun getAll() {
        notificationInteractor.getAll()
            .observeOn(RxSchedulers.main())
            .subscribe(viewState::setChatsList, viewState::showError)
            .addFullLifeCycle()

    }

    fun onAddNotificationClick() {
        router.navigateTo(Flows.Notifications.SCREEN_EDIT_NOTIFICATION)
    }
}