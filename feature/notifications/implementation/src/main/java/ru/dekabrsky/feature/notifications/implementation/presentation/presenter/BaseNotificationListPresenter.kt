package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationMedicineEntity
import ru.dekabrsky.feature.notifications.common.presentation.model.NotificationsFlowArgs
import ru.dekabrsky.feature.notifications.common.utils.NotificationToStringFormatter
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.DoctorNotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.INotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.NotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationsListView
import ru.dekabrsky.feature.notifications.implementation.receiver.NotificationsReceiver
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withCustomLoadingViewIf
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import java.util.Calendar
import javax.inject.Inject

abstract class BaseNotificationListPresenter<T: NotificationsListView>(
    private val router: FlowRouter,
    private val interactor: INotificationInteractor,
    private val flowArgs: NotificationsFlowArgs,
    private val formatter: NotificationToStringFormatter
) : BasicPresenter<T>(router) {

    private var isFirstLoading = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (flowArgs.parentScopeName != Scopes.SCOPE_APP) {
            viewState.setToolbarBackButton()
        }
    }

    override fun attachView(view: T) {
        super.attachView(view)
        getAll()
    }

    private fun getAll() {
        interactor.getAll()
            .observeOn(RxSchedulers.main())
            .withCustomLoadingViewIf(viewState::setListLoadingVisibility, isFirstLoading)
            .subscribe(::dispatchNotifications, viewState::showError)
            .addFullLifeCycle()
    }

    protected open fun dispatchNotifications(list: List<NotificationEntity>) {
        isFirstLoading = false
        viewState.setChatsList(list)
        viewState.setEmptyLayoutVisibility(list.isEmpty())
    }

    fun onAddNotificationClick() {
        router.navigateTo(Flows.Notifications.SCREEN_EDIT_NOTIFICATION)
    }

    fun onNotificationDelete(notificationEntity: NotificationEntity) {
        interactor.delete(notificationEntity)
            .observeOn(RxSchedulers.main())
            .subscribe(
                { dispatchNotificationDelete(notificationEntity) },
                viewState::showError
            )
            .addFullLifeCycle()
    }

    protected open fun dispatchNotificationDelete(notificationEntity: NotificationEntity) {
        getAll()
    }

    fun onNotificationClick(notificationEntity: NotificationEntity) {
        router.navigateTo(Flows.Notifications.SCREEN_EDIT_NOTIFICATION, notificationEntity)
    }

    fun onItemCheckedChanged(notificationEntity: NotificationEntity, isEnabled: Boolean) {
        interactor.update(notificationEntity.copy(enabled = isEnabled))
            .observeOn(RxSchedulers.main())
            .subscribe({ getAll() }, viewState::showError)
            .addFullLifeCycle()
    }

    fun formatDosage(medicineEntity: NotificationMedicineEntity) =
        formatter.formatDosage(medicineEntity)
}