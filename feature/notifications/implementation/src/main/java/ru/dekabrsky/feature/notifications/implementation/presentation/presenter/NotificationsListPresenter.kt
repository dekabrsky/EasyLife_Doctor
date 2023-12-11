package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import ru.dekabrsky.feature.notifications.common.model.NotificationsFlowArgs
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.NotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationsListView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withCustomLoadingViewIf
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import javax.inject.Inject

class NotificationsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val notificationInteractor: NotificationInteractor,
    private val flowArgs: NotificationsFlowArgs
): BasicPresenter<NotificationsListView>(router) {

    private var isFirstLoading = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (flowArgs.parentScopeName != Scopes.SCOPE_APP) {
            viewState.setToolbarBackButton()
        }
    }

    override fun attachView(view: NotificationsListView) {
        super.attachView(view)
        getAll()
    }

    private fun getAll() {
        notificationInteractor.getAll()
            .observeOn(RxSchedulers.main())
            .withCustomLoadingViewIf(viewState::setListLoadingVisibility, isFirstLoading)
            .subscribe(::dispatchNotifications, viewState::showError)
            .addFullLifeCycle()

    }

    private fun dispatchNotifications(list: List<NotificationEntity>) {
        isFirstLoading = false
        viewState.setChatsList(list)
        viewState.setEmptyLayoutVisibility(list.isEmpty())
    }

    fun onAddNotificationClick() {
        router.navigateTo(Flows.Notifications.SCREEN_EDIT_NOTIFICATION)
    }

    fun onNotificationDelete(notificationEntity: NotificationEntity) {
        notificationInteractor.delete(notificationEntity)
            .observeOn(RxSchedulers.main())
            .subscribe({ getAll() }, viewState::showError)
            .addFullLifeCycle()
    }

    fun onNotificationClick(notificationEntity: NotificationEntity) {
        router.navigateTo(Flows.Notifications.SCREEN_EDIT_NOTIFICATION, notificationEntity)
    }

    fun onItemCheckedChanged(notificationEntity: NotificationEntity, isEnabled: Boolean) {
        notificationInteractor.update(notificationEntity.copy(enabled = isEnabled))
            .observeOn(RxSchedulers.main())
            .subscribe({ getAll() }, viewState::showError)
            .addFullLifeCycle()
    }
}