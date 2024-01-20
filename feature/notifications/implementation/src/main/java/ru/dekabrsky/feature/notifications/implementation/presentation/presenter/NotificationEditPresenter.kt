package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import main.utils.isTrue
import main.utils.orZero
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.NotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.mapper.NotificationEntityToUiMapper
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationEditView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withLoadingView
import javax.inject.Inject

class NotificationEditPresenter @Inject constructor(
    router: FlowRouter,
    private val interactor: NotificationInteractor,
    private val existingNotification: NotificationEntity,
    private val mapper: NotificationEntityToUiMapper
) : BasicPresenter<NotificationEditView>(router) {

    private var notification = existingNotification.uid?.let {
        mapper.mapEntityToUi(existingNotification)
    } ?: NotificationEditUiModel()

    private val daysInWeek = listOf(
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        notification.let {
            viewState.setNotesFields(it)
            it.hour?.let { hour -> it.minute?.let { minute -> onTimeSet(hour, minute) } }
            if (notification.selectedDays.isEmpty()) {
                viewState.setSelectedDays(daysInWeek.map { it.value % DAYS_IN_WEEK + 1 })
            } else {
                viewState.setSelectedDays(it.selectedDays.map { it.value % DAYS_IN_WEEK + 1 })
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
                    onBackPressed()
                }, { viewState.showError(it) })
                .addFullLifeCycle()
        } else {
            interactor.update(result)
                .observeOn(RxSchedulers.main())
                .withLoadingView(viewState)
                .subscribe({
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
        notification.selectedDays = selectedDays.map { daysInWeek[it - 1] }
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

    companion object {
        private const val DAYS_IN_WEEK = 7
    }
}