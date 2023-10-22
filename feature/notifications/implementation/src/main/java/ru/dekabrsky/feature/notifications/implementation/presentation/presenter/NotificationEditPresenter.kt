package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.dekabrsky.feature.notifications.common.receiver.NotificationsReceiver
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.NotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.mapper.NotificationEntityToUiMapper
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationEditView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import java.util.Calendar
import javax.inject.Inject

class NotificationEditPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: NotificationInteractor,
    private val existingNotification: NotificationEntity,
    private val mapper: NotificationEntityToUiMapper,
    private val context: Context
) : BasicPresenter<NotificationEditView>(router) {

    private var notification = existingNotification.uid?.let {
        mapper.mapEntityToUi(existingNotification)
    } ?: NotificationEditUiModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        notification.let {
            viewState.setNotesFields(it)
            onTimeSet(it.hour, it.minute)
        }
    }

    fun onTabletNameChanged(tabletName: String) {
        notification.tabletName = tabletName
    }

    fun onDosageChanged(dosage: String) {
        notification.dosage = dosage
    }

    fun onNoteChanged(note: String) {
        notification.note = note
    }

    fun onTimeClick() {
        viewState.showTimePicker(notification.hour, notification.minute)
    }

    fun onDoneClick() {
        val result = mapper.mapUiToEntity(notification, existingNotification.uid)

        if (existingNotification.uid == null) {
            interactor.add(result)
                .observeOn(RxSchedulers.main())
                .subscribe({
                    dispatchEvent(it)
                    onBackPressed()
                }, { viewState.showError(it) })
                .addFullLifeCycle()
        } else {
            interactor.update(result)
                .observeOn(RxSchedulers.main())
                .subscribe({
                    dispatchEvent(existingNotification.uid)
                    onBackPressed()
                }, { viewState.showError(it) })
                .addFullLifeCycle()
        }


    }

    private fun dispatchEvent(id: Long? = null) {
        if (id == null) return

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        calendar.set(Calendar.HOUR_OF_DAY, notification.hour)
        calendar.set(Calendar.MINUTE, notification.minute)
        calendar.set(Calendar.SECOND, 0)

        val notifyIntent = Intent(context, NotificationsReceiver::class.java)
        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            notifyPendingIntent
        )
    }

    fun onTimeSet(hourOfDay: Int, minute: Int) {
        notification.hour = hourOfDay
        notification.minute = minute
        viewState.setTime(notification.time)
    }
}