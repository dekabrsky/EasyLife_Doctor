package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import main.utils.isTrue
import main.utils.orZero
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import ru.dekabrsky.feature.notifications.common.receiver.NotificationsReceiver
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.NotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.mapper.NotificationEntityToUiMapper
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationEditView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withLoadingView
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
            it.hour?.let { hour -> it.minute?.let { minute -> onTimeSet(hour, minute) } }
            if (notification.selectedDays.isEmpty()) {
                viewState.setSelectedDays(DayOfWeek.values().map { it.value })
            } else {
                viewState.setSelectedDays(it.selectedDays.map { it.value })
            }
            if (it.withDuration) {
                viewState.setDurationFieldsVisibility(true)
                viewState.setDurationSwitchIsChecked(true)
                updateDurationFieldsByModel()
            }
            viewState.setNotificationEnabled(it.enabled)
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
        viewState.showTimePicker(notification.hour.orZero(), notification.minute.orZero())
    }

    fun onDoneClick() {
        if (validate().not()) return

        val result = mapper.mapUiToEntity(notification, existingNotification.uid)

        if (existingNotification.uid == null) {
            interactor.add(result)
                .observeOn(RxSchedulers.main())
                .withLoadingView(viewState)
                .subscribe({
                    dispatchEvent(it.uid)
                    onBackPressed()
                }, { viewState.showError(it) })
                .addFullLifeCycle()
        } else {
            interactor.update(result)
                .observeOn(RxSchedulers.main())
                .withLoadingView(viewState)
                .subscribe({
                    dispatchEvent(existingNotification.uid)
                    onBackPressed()
                }, { viewState.showError(it) })
                .addFullLifeCycle()
        }


    }

    private fun validate(): Boolean {
        val errors = mutableListOf<String>().apply {
            if (notification.tabletName.isEmpty()) add("Введите название лекарства")
            if (notification.hour == null) add("Выберите время")
            if (notification.selectedDays.isEmpty()) add("Выберите минимум один день недели")
            if (notification.withDuration && (notification.endDate == null || notification.startDate == null)) {
                add("Укажите длительность курса")
            }
            if (notification.endDate?.isBefore(notification.startDate).isTrue()) add("Время начала позже времени конца")
        }

        if (errors.isNotEmpty()) {
            viewState.showToast(errors.joinToString("\n"))
            return false
        }
        return true
    }

    private fun dispatchEvent(id: Long? = null) {
        if (id == null) return
        val hour = notification.hour ?: return
        val minute = notification.minute ?: return

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
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

    fun onStartDateSet(date: LocalDate) {
        notification.startDate = date
        viewState.setStartDate(notification.startDateString)
    }

    fun onEndDateSet(date: LocalDate) {
        notification.endDate = date
        viewState.setEndDate(notification.endDateString)
    }

    fun onCheckedDaysChanged(selectedDays: MutableList<Int>) {
        notification.selectedDays = selectedDays.map { DayOfWeek.values()[it - 1] }
    }

    fun onStartDateClick() =
        viewState.showStartDatePicker(notification.startDate ?: LocalDate.now())

    fun onEndDateClick() =
        viewState.showEndDatePicker(notification.endDate ?: LocalDate.now())

    fun onDurationCheckedChanged(checked: Boolean) {
        notification.withDuration = checked
        viewState.setDurationFieldsVisibility(checked)
        notification.startDate = null
        notification.endDate = null
        updateDurationFieldsByModel()
    }

    private fun updateDurationFieldsByModel() {
        viewState.setStartDate(notification.startDateString)
        viewState.setEndDate(notification.endDateString)
    }

    fun onEnabledCheckedChanged(checked: Boolean) {
        notification.enabled = checked
    }
}