package ru.dekabrsky.feature.notifications.implementation.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import org.threeten.bp.LocalDate
import ru.dekabrsky.easylife.basic.fragments.BasicView

@AddToEndSingle
interface NotificationEditView: BasicView {
    fun setTime(time: String)
    @OneExecution
    fun showTimePicker(hour: Int, minute: Int)
    fun showStartDatePicker(date: LocalDate)
    fun showEndDatePicker(date: LocalDate)
    fun setStartDate(startDateString: String)
    fun setEndDate(endDateString: String)
    fun setDurationFieldsVisibility(checked: Boolean)
    fun setDurationSwitchIsChecked(checked: Boolean)
    fun setNotificationEnabled(enabled: Boolean)
    fun updateMedicines()
    fun showDaysDialog(selectedVariantIndices: BooleanArray, variants: Array<String>)
    fun showSelectedDays(selectedDays: String)
}