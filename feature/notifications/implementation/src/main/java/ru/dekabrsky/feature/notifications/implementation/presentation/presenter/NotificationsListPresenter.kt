package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import main.utils.orZero
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.model.NotificationsFlowArgs
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


class NotificationsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val notificationInteractor: NotificationInteractor,
    private val context: Context,
    private val flowArgs: NotificationsFlowArgs,
    private val sharedPreferencesProvider: SharedPreferencesProvider
) : BasicPresenter<NotificationsListView>(router) {

    private var isFirstLoading = true
    private var notificationIds = sharedPreferencesProvider.notificationIds
    private var notificationIdsCache: MutableSet<Long> = mutableSetOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (flowArgs.parentScopeName != Scopes.SCOPE_APP) {
            viewState.setToolbarBackButton()
        }

        notificationIds.get().forEach {
            cancelNotification(it)
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

        list.filter { it.enabled }.forEach { notification ->
            addNotification(notification)
        }

        notificationIds.set(notificationIdsCache)
    }

    private fun addNotification(notificationEntity: NotificationEntity) {
        notificationIdsCache.add(notificationEntity.uid.orZero())

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val hour = notificationEntity.hour
        val minute = notificationEntity.minute

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        val notificationTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute))

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        if (LocalDateTime.now() > notificationTime) {
            calendar.set(Calendar.DAY_OF_YEAR, LocalDate.now().dayOfYear + 1)
        }

        val notifyIntent = Intent(context, NotificationsReceiver::class.java)

        notifyIntent.putExtras(
            Bundle().apply {
                putLong("NOTIFICATION_TIME", calendar.timeInMillis)
                putLong("NOTIFICATION_ID", notificationEntity.uid.orZero())
                putSerializable("NOTIFICATION", notificationEntity)
            }
        )

        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationEntity.uid?.toInt().orZero(),
            notifyIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        Log.d("addNotification: ", notificationEntity.uid?.toInt().orZero().toString())

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, notifyPendingIntent)

    }

    private fun cancelNotification(notificationUid: Long) {
        notificationIdsCache.remove(notificationUid)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val notificationIntent = Intent(context, NotificationsReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationUid.toInt().orZero(),
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
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

    companion object {
        private const val NOTIFICATIONS_LIST: String = "NOTIFICATIONS_LIST"
    }
}